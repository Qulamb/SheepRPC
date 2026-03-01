package com.xiaoyang.Client.proxy;

import com.xiaoyang.Client.netty.NettyRPCClient;
import com.xiaoyang.Client.retry.guavaRetry;
import com.xiaoyang.Client.rpcClient.RPCClient;
import com.xiaoyang.Client.serviceCenter.ServiceCenter;
import com.xiaoyang.Client.serviceCenter.ZkServiceCenter;
import com.xiaoyang.Common.Message.RPCRequest;
import com.xiaoyang.Common.Message.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    private NettyRPCClient nettyRPCClient;
    private ServiceCenter serviceCenter;
    public ClientProxy() throws InterruptedException {
        serviceCenter=new ZkServiceCenter();
        nettyRPCClient=new NettyRPCClient();
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //构建request
        RPCRequest request = RPCRequest.builder()
                .methodName(method.getName())
                .interfaceName(method.getDeclaringClass().getName())
                .params(args)
                .paramsTypes(method.getParameterTypes())
                .build();
        //将request发送到服务端
        RPCResponse response;
        if(serviceCenter.checkRetry(request.getInterfaceName())){
            response=new guavaRetry().sendServiceWithRetry(request, nettyRPCClient);
        }else {
            response=nettyRPCClient.sendRequest(request);
        }
        return response.getData();
    }

    public <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}

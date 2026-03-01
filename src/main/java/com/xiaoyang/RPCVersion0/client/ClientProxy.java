package com.xiaoyang.RPCVersion0.client;

import com.xiaoyang.RPCVersion0.common.RPCRequest;
import com.xiaoyang.RPCVersion0.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    private NettyRPCClient nettyRPCClient;
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
        RPCResponse response = nettyRPCClient.sendRequest(request);
        return response.getData();
    }

    public <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}

package com.xiaoyang.RPCVersion0.Test;

import com.xiaoyang.RPCVersion0.server.BlogServiceImpl;
import com.xiaoyang.RPCVersion0.server.NettyRPCServer;
import com.xiaoyang.RPCVersion0.server.ServiceProvider;
import com.xiaoyang.RPCVersion0.server.UserServiceImpl;
import com.xiaoyang.RPCVersion0.service.BlogService;
import com.xiaoyang.RPCVersion0.service.UserService;

//测试服务端
public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
        //map存储接口名（一般使用接口的全限定名以保证在分布式环境下的唯一性）和实现类的实例
        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1",8899);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        //SimpleRPCServer RPCServer = new SimpleRPCServer(serviceProvider.getInterfaceProvider());
        NettyRPCServer rpcServer = new NettyRPCServer(serviceProvider);
        rpcServer.start(8899);
    }
}

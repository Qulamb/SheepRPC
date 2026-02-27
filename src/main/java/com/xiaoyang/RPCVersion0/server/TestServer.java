package com.xiaoyang.RPCVersion0.server;

import com.xiaoyang.RPCVersion0.service.BlogService;
import com.xiaoyang.RPCVersion0.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
        //map存储接口名（一般使用接口的全限定名以保证在分布式环境下的唯一性）和实现类的实例
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        //SimpleRPCServer RPCServer = new SimpleRPCServer(serviceProvider.getInterfaceProvider());
        NettyRPCServer rpcServer = new NettyRPCServer(serviceProvider);
        rpcServer.start(8899);
    }
}

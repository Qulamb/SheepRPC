package com.xiaoyang.RPCVersion0.client;

import com.xiaoyang.RPCVersion0.common.User;
import com.xiaoyang.RPCVersion0.service.UserService;

public class TestClient {
    public static void main(String[] args) {
        SimpleRPCClient simpleRPCClient = new SimpleRPCClient(8899, "127.0.0.1");
        ClientProxy clientProxy = new ClientProxy(simpleRPCClient);
        UserService proxy = clientProxy.getProxy(UserService.class);
        User user = proxy.getUserByUserId(10);
        System.out.println("User为"+user);
    }
}

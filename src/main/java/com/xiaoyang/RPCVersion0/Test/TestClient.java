package com.xiaoyang.RPCVersion0.Test;

import com.xiaoyang.RPCVersion0.client.ClientProxy;
import com.xiaoyang.RPCVersion0.client.NettyRPCClient;
import com.xiaoyang.RPCVersion0.common.Blog;
import com.xiaoyang.RPCVersion0.common.User;
import com.xiaoyang.RPCVersion0.service.BlogService;
import com.xiaoyang.RPCVersion0.service.UserService;

public class TestClient {
    public static void main(String[] args) {
        NettyRPCClient nettyRPCClient = new NettyRPCClient();
        ClientProxy clientProxy = new ClientProxy(nettyRPCClient);
        UserService proxy = clientProxy.getProxy(UserService.class);
         User user = proxy.getUserByUserId(1);
        System.out.println("User为"+user);
        User user2 = proxy.getUserByUserId(2);
        System.out.println("User为"+user);
        User user3 = proxy.getUserByUserId(3);
        System.out.println("User为"+user);
        User user4 = proxy.getUserByUserId(4);
        System.out.println("User为"+user);
        BlogService blog = clientProxy.getProxy(BlogService.class);
        Blog blogId = blog.getBlogById(1);
        System.out.println("客户端查询了"+blogId+"博客");
    }
}

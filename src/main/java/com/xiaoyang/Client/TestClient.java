package com.xiaoyang.Client;

import com.xiaoyang.Client.proxy.ClientProxy;
import com.xiaoyang.Client.netty.NettyRPCClient;
import com.xiaoyang.Common.pojo.Blog;
import com.xiaoyang.Common.pojo.User;
import com.xiaoyang.Server.service.BlogService;
import com.xiaoyang.Server.service.UserService;

public class TestClient {
    public static void main(String[] args) throws InterruptedException {
        ClientProxy clientProxy = new ClientProxy();
        UserService proxy = clientProxy.getProxy(UserService.class);
         User user = proxy.getUserByUserId(1);
        System.out.println("User为"+user);
        BlogService blog = clientProxy.getProxy(BlogService.class);
        Blog blogId = blog.getBlogById(1);
        System.out.println("客户端查询了"+blogId+"博客");

    }
}

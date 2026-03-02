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

/*        ClientProxy clientProxy=new ClientProxy();
        UserService proxy=clientProxy.getProxy(UserService.class);
        for(int i = 0; i < 120; i++) {
            Integer i1 = i;
            if (i%30==0) {
                Thread.sleep(10000);
            }
            new Thread(()->{
                try{
                    User user = proxy.getUserByUserId(i1);

                    System.out.println("从服务端得到的user="+user.toString());

                    Integer id = proxy.insertUserId(User.builder().id(i1).userName("User" + i1.toString()).sex(true).build());
                    System.out.println("向服务端插入user的id:"+id);
                } catch (NullPointerException e){
                    System.out.println("user为空");
                    e.printStackTrace();
                }
            }).start();
        }*/
    }
}

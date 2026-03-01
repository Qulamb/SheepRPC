package com.xiaoyang.Client.rpcClient;

import com.xiaoyang.Common.Message.RPCRequest;
import com.xiaoyang.Common.Message.RPCResponse;

//客户端
public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);

    /*public static void main(String[] args){
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        //获得UserService的代理对象
        UserService proxy = clientProxy.getProxy(UserService.class);

        //user服务的方法一
        User user = proxy.getUserByUserId(10);
        System.out.println(user);
        //user服务的方法二
        Integer integer = proxy.insertUserId(User.builder()
                .id(1)
                .userName("小羊")
                .sex(true)
                .build());
        System.out.println("向服务端插入数据"+integer);

        //blog服务的方法一
        BlogService blogService = clientProxy.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(1000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }*/
}

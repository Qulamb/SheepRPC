package com.xiaoyang.RPCVersion0.client;

import com.xiaoyang.RPCVersion0.common.User;
import com.xiaoyang.RPCVersion0.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
//客户端
public class RPCClient {
    public static void main(String[] args){
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        //获得UserService的代理对象
        UserService proxy = clientProxy.getProxy(UserService.class);

        //服务的方法一
        User user = proxy.getUserByUserId(10);
        System.out.println(user);
        //服务的方法二
        Integer integer = proxy.insertUserId(User.builder()
                .id(1)
                .userName("小羊")
                .sex(true)
                .build());
        System.out.println("向服务端插入数据"+integer);
    }
}

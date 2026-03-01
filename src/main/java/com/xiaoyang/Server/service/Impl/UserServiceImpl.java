package com.xiaoyang.Server.service.Impl;

import com.xiaoyang.Common.pojo.User;
import com.xiaoyang.Server.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    @Override
    public User getUserByUserId(Integer id) {
        System.out.println("客户端查询了"+id+"的用户");
        //模拟从数据库中取数据的行为
        Random random=new Random();
        return User.builder().userName(UUID.randomUUID().toString())
                .id(id)
                .sex(random.nextBoolean())
                .build();
    }

    @Override
    public Integer insertUserId(User user) {
        System.out.println("插入数据成功");
        return 1;
    }
}

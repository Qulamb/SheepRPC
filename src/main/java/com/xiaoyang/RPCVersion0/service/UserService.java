package com.xiaoyang.RPCVersion0.service;

import com.xiaoyang.RPCVersion0.common.User;

public interface UserService {
    // 客户端通过这个接口调用服务端的实现类
    User getUserByUserId(Integer id);
    //
    Integer insertUserId(User user);
}

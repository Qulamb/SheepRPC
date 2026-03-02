package com.xiaoyang.Server.ratelimit;

public interface RateLimit {
    //获取令牌
    boolean getToken();
}

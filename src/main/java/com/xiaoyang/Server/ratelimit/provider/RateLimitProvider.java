package com.xiaoyang.Server.ratelimit.provider;
//提供速率限制相关的服务

import com.xiaoyang.Server.ratelimit.RateLimit;
import com.xiaoyang.Server.ratelimit.impl.TokenBucketRateLimitImpl;

import java.util.HashMap;
import java.util.Map;

public class RateLimitProvider {
    private Map<String, RateLimit> rateLimitMap=new HashMap<>();

    /**
     * 根据接口名称获取其对应的速率限制器实例，没有则创建一个返回
     * @param interfaceName 接口名称
     * @return 速率限制器实例
     */
    public RateLimit getRateLimit(String interfaceName){
        if(!rateLimitMap.containsKey(interfaceName)){
            RateLimit rateLimit=new TokenBucketRateLimitImpl(100,10);
            rateLimitMap.put(interfaceName,rateLimit);
            return rateLimit;
        }
        return rateLimitMap.get(interfaceName);
    }
}

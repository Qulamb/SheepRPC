package com.xiaoyang.Server.ratelimit.impl;

import com.xiaoyang.Server.ratelimit.RateLimit;

public class TokenBucketRateLimitImpl implements RateLimit {
    //令牌产生速率（单位为ms）
    private static  int RATE;
    //桶容量
    private static  int CAPACITY;
    //当前桶容量
    private volatile int curCapcity;
    //时间戳
    private volatile long timeStamp=System.currentTimeMillis();
    public TokenBucketRateLimitImpl(int rate,int capacity){
        RATE=rate;
        CAPACITY=capacity;
        curCapcity=capacity;
    }
    @Override
    public synchronized boolean getToken() {
        //桶内还有令牌，消费一个令牌
        if(curCapcity>0){
            curCapcity--;
            return true;
        }
        //桶内没有令牌了
        long current = System.currentTimeMillis();
        //判断距离上次取令牌是否已经过去足够的时间
        if(current-timeStamp>=RATE) {
            if((current-timeStamp)/RATE>=2){
                curCapcity += (int) ((current - timeStamp) / RATE) - 1;
            }


            if (curCapcity > CAPACITY) {
                current = CAPACITY;
            }
            timeStamp = current;
            return true;
        }
        return false;
    }
}

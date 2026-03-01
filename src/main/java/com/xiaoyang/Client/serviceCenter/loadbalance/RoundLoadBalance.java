package com.xiaoyang.Client.serviceCenter.loadbalance;

import java.util.List;

public class RoundLoadBalance implements LoadBalance{
    private int choose=-1;
    @Override
    public String balance(List<String> addressList) {
        int count=addressList.size();
        choose++;
        choose=choose%count;
        return addressList.get(choose);
    }
}

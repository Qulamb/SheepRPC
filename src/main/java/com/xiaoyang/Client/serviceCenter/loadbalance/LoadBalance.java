package com.xiaoyang.Client.serviceCenter.loadbalance;

import java.util.List;

public interface LoadBalance {
    String balance(List<String> addressList);
}

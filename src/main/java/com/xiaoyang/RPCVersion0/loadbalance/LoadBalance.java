package com.xiaoyang.RPCVersion0.loadbalance;

import java.util.List;

public interface LoadBalance {
    String balance(List<String> addressList);
}

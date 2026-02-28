package com.xiaoyang.RPCVersion0.codec;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum SerializerType {
    JavaSer(0),
    JsonSer(1);
    private int code;
    public int getCode(){
        return code;
    }
}

package com.xiaoyang.Common.serializer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SerializerType {
    JavaSer(0),
    JsonSer(1);
    private int code;
    public int getCode(){
        return code;
    }
}

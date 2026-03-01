package com.xiaoyang.Common.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Data
@Builder
@AllArgsConstructor
//服务端返回的信息类型
public class RPCResponse implements Serializable {
    //状态信息
    private int code;
    private String message;
    private Class<?> DataType;
    //具体数据
    private Object data;
    public static RPCResponse success(Object data){
        return RPCResponse.builder().code(200).data(data).DataType(data.getClass()).build();
    }
    public static RPCResponse fail() {
        return RPCResponse.builder().code(500).message("服务器发生错误").build();
    }
}

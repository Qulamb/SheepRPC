package com.xiaoyang.RPCVersion0.codec;

public interface Serializer {
    //将对象转化成字节数组
    byte[] serialize(Object obj);
    //将字节数组反序列化成对象，需要指定转化成对象的类型
    Object deserialize(byte[] bytes,int messageType);
    //返回使用的序列化器类型
    //0：java自带的序列化方式，1：json序列化方式
    int getType();
    //根据序号取出序列化器
    static Serializer getSerializerByCode(int code){
        switch (code){
            case 0:
                return new ObjectSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}

package com.xiaoyang.RPCVersion0.codec;

import com.xiaoyang.RPCVersion0.common.RPCRequest;
import com.xiaoyang.RPCVersion0.common.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

//自定义消息格式：2字节消息类型，2字节序列化方式，4字节消息长度
@AllArgsConstructor
public class RpcEncode extends MessageToByteEncoder<Object> {
    private Serializer serializer;
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {
        System.out.println(msg.getClass());
        //写入消息类型
        if(msg instanceof RPCRequest){
            byteBuf.writeShort(MessageType.MESSAGE_TYPE_REQUEST);
        }
        else if(msg instanceof RPCResponse){
            byteBuf.writeShort(MessageType.MESSAGE_TYPE_RESPONSE);
        }
        //写入序列化方式
        byteBuf.writeShort(serializer.getType());
        // 得到序列化数组
        byte[] serialize = serializer.serialize(msg);
        // 写入长度
        byteBuf.writeInt(serialize.length);
        // 写入序列化字节数组
        byteBuf.writeBytes(serialize);
    }
}

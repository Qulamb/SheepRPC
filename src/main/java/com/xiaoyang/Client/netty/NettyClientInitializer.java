package com.xiaoyang.Client.netty;

import com.xiaoyang.Common.serializer.JsonSerializer;
import com.xiaoyang.Common.serializer.RpcDecode;
import com.xiaoyang.Common.serializer.RpcEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 *每个新建立的客户端连接都会触发一次这个方法（因为在handler内）
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //使用自定义的编解码器
        pipeline.addLast(new RpcDecode());
        // 编码需要传入序列化器，这里是json，还支持ObjectSerializer，也可以自己实现其他的
        pipeline.addLast(new RpcEncode(new JsonSerializer()));
        pipeline.addLast(new NettyClientHandler());

    }
}

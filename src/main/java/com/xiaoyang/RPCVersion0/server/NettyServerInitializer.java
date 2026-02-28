package com.xiaoyang.RPCVersion0.server;


import com.xiaoyang.RPCVersion0.codec.JsonSerializer;
import com.xiaoyang.RPCVersion0.codec.RpcDecode;
import com.xiaoyang.RPCVersion0.codec.RpcEncode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer {
    private ServiceProvider serviceProvider;
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //使用自定义的编解码器
        pipeline.addLast(new RpcDecode());
        // 编码需要传入序列化器，这里是json，还支持ObjectSerializer，也可以自己实现其他的
        pipeline.addLast(new RpcEncode(new JsonSerializer()));
        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}

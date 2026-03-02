package com.xiaoyang.Client.netty;

import com.xiaoyang.Client.rpcClient.RPCClient;
import com.xiaoyang.Common.Message.RPCRequest;
import com.xiaoyang.Common.Message.RPCResponse;
import com.xiaoyang.Client.serviceCenter.ServiceCenter;
import com.xiaoyang.Client.serviceCenter.ZkServiceCenter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * 实现RPCClient接口
 * 使用Channel连接池技术，避免频繁TCP握手开销
 */
public class NettyRPCClient implements RPCClient {
    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;
    private static final ChannelPoolManager poolManager;
    private ServiceCenter serviceCenter;

    public NettyRPCClient(String host, int port) throws InterruptedException {
        this.serviceCenter = new ZkServiceCenter();
    }

    public NettyRPCClient() throws InterruptedException {
        this.serviceCenter = new ZkServiceCenter();
    }

    // netty客户端初始化，重复使用
    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
        // 初始化连接池管理器，最大连接数10
        poolManager = new ChannelPoolManager(bootstrap, 10);
    }

    /**
     * 使用连接池发送请求
     * 从连接池获取Channel，避免频繁TCP握手
     */
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        InetSocketAddress address = serviceCenter.serviceDiscovery(request.getInterfaceName());
        Channel channel = null;

        try {
            // 从连接池获取Channel
            channel = poolManager.acquireChannel(address);

            // 发送数据
            channel.writeAndFlush(request);
            channel.closeFuture().sync();

            // 阻塞的获得结果，通过给channel设计别名，获取特定名字下的channel中的内容
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();

            System.out.println(response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放Channel回连接池
            if (channel != null) {
                poolManager.releaseChannel(address, channel);
            }
        }
        return null;
    }

    /**
     * 关闭连接池和EventLoopGroup
     */
    public void close() {
        poolManager.closeAll();
        eventLoopGroup.shutdownGracefully();
    }
}
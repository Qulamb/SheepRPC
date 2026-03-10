package com.xiaoyang.Client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Channel连接池管理器
 * 基于阻塞队列实现简单的Channel复用池
 */
public class ChannelPoolManager {
    // 每个地址对应的Channel队列
    private final ConcurrentHashMap<String, BlockingQueue<Channel>> poolMap = new ConcurrentHashMap<>();
    private final Bootstrap bootstrap;
    private final int maxConnections;
    private final ConcurrentHashMap<String, Integer> currentConnections = new ConcurrentHashMap<>();

    public ChannelPoolManager(Bootstrap bootstrap, int maxConnections) {
        this.bootstrap = bootstrap;
        this.maxConnections = maxConnections;
    }

    /**
     * 获取或创建Channel
     */
    public Channel acquireChannel(InetSocketAddress address) throws InterruptedException {
        String key = address.toString();
        BlockingQueue<Channel> queue = poolMap.computeIfAbsent(key, k -> new LinkedBlockingQueue<>());

        // 尝试从池中获取可用Channel
        Channel channel = queue.poll();
        if (channel != null && channel.isActive()) {
            System.out.println("从连接池复用Channel: " + channel.id());
            return channel;
        }

        // 池中没有可用连接，检查是否达到最大连接数
        Integer current = currentConnections.getOrDefault(key, 0);
        if (current < maxConnections) {
            // 创建新连接
            currentConnections.put(key, current + 1);
            ChannelFuture future = bootstrap.connect(address).sync();
            channel = future.channel();
            System.out.println("创建新Channel: " + channel.id());
            return channel;
        }

        // 达到最大连接数，阻塞等待
        System.out.println("等待可用Channel...");
        return queue.take();
    }

    /**
     * 释放Channel回连接池
     */
    public void releaseChannel(InetSocketAddress address, Channel channel) {
        if (channel == null || !channel.isActive()) {
            // Channel已关闭，减少计数
            String key = address.toString();
            Integer current = currentConnections.getOrDefault(key, 0);
            if (current > 0) {
                currentConnections.put(key, current - 1);
            }
            return;
        }

        String key = address.toString();
        BlockingQueue<Channel> queue = poolMap.get(key);
        if (queue != null) {
            queue.offer(channel);
            System.out.println("Channel释放回连接池: " + channel.id());
        }
    }

    /**
     * 关闭指定地址的连接池
     */
    public void closePool(InetSocketAddress address) {
        String key = address.toString();
        BlockingQueue<Channel> queue = poolMap.remove(key);
        if (queue != null) {
            queue.forEach(Channel::close);
            queue.clear();
        }
        currentConnections.remove(key);
        System.out.println("关闭连接池: " + address);
    }

    /**
     * 关闭所有连接
     */
    public void closeAll() {
        poolMap.forEach((key, queue) -> {
            queue.forEach(Channel::close);
            queue.clear();
        });
        poolMap.clear();
        currentConnections.clear();
        System.out.println("关闭所有连接池");
    }

    /**
     * 获取连接池状态
     */
    public String getPoolStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("连接池数量: ").append(poolMap.size()).append("\n");
        poolMap.forEach((key, queue) -> {
            sb.append("  - ").append(key).append(": 可用连接=").append(queue.size())
              .append(", 总连接=").append(currentConnections.getOrDefault(key, 0)).append("\n");
        });
        return sb.toString();
    }
}
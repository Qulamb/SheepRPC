package com.xiaoyang.RPCVersion0.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
//这个实现类负责BIO监听，来一个任务就new一个线程去执行.处理任务的工作见WorkThread中
public class SimpleRPCServer implements RPCServer{

    private Map<String,Object> serviceProvide;

    public SimpleRPCServer(Map<String, Object> serviceProvide) {
        this.serviceProvide = serviceProvide;
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端启动了");
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(new WorkThread(socket,serviceProvide)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }

    }

    @Override
    public void stop() {

    }
}

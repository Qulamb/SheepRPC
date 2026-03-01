package com.xiaoyang.Server.server;

public interface RPCServer {
    void start(int port);
    void stop();
}

//服务端
/*public class RPCServer {
    public static void main(String[] args){
        UserServiceImpl userService = new UserServiceImpl();
        try {
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务端启动了");
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("有客户端连接成功");
                //开启一个线程去处理
                new Thread(()->
                {
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        RPCRequest request =(RPCRequest)ois.readObject();
                        //这里不知道客户端调用哪个方法，所以不能写死。
                        //反射调用对应方法
                        Method method=userService.getClass().getMethod(request.getMethodName(),request.getParamsTypes());
                        Object invoke = method.invoke(userService, request.getParams());
                        oos.writeObject(RPCResponse.success(invoke));
                        oos.flush();
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                             InvocationTargetException  e) {
                        e.printStackTrace();
                        System.out.println("从IO中读取数据错误");
                    }
                }
                ).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }
}*/

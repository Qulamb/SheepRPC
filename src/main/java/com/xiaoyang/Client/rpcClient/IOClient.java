package com.xiaoyang.Client.rpcClient;

import com.xiaoyang.Common.Message.RPCRequest;
import com.xiaoyang.Common.Message.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IOClient {
    public static RPCResponse sendRequest(String host, int port, RPCRequest request){
        try {
            Socket socket=new Socket(host,port);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);
            oos.flush();

            RPCResponse response = (RPCResponse) ois.readObject();
            return response;

        } catch (IOException|ClassNotFoundException e) {
            System.out.println();
            return null;
        }

    }
}

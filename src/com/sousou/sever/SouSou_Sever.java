package com.sousou.sever;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端控制类
 *
 * @author kinlon
 * @version 171031-17
 */
public class SouSou_Sever {

    public static void main(String[] args) {
        Socket socket;
        ServerSocket serverSocket;

        //设置监听端口
        int port = 6661;

        Log.log("服务端", "启动");

        try {
            //创建监听对象
            serverSocket = new ServerSocket(port);

            //进入监听状态
            while (true) {

                //监听等待
                Log.log("服务端", port + "端口监听中...");
                socket = serverSocket.accept();

                //输出监听到的客户端信息
                Log.log("客户端接入", socket.getInetAddress().getHostAddress() + "\n" + socket.getInetAddress().getHostName());

                //检测到客户端访问，创建线程接待客户端
                ServerThread serverThread = new ServerThread(socket);

                //开始接待客户端
                serverThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

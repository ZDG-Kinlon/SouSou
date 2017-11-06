package com.sousou.sever;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 服务端线程类，接收客户端发送过来的消息，并进行反馈
 *
 *
 */
public class ServerThread extends Thread {
    //创建socket对象
    private Socket socket;
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;
    private OutputStream os;
    private StringBuffer receiveMsg;
    Function obj;


    /**
     * 构造方法
     *
     * @param socket
     */
    public ServerThread(Socket socket) {
        obj = new Function();
        //初始化socket对象
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //1.创建输入流，创建输出流
            is = socket.getInputStream();
            os = socket.getOutputStream();

            //2.根据字节流创建字符流
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            //3.创建消息接收
            String temp;

            //4.读取消息，并输出详情
            this.receiveMsg = new StringBuffer();
            do {
                temp = br.readLine();
                if (temp == null) {
                    break;
                } else {
                    this.receiveMsg.append("").append(temp);
                }
            } while (true);
            temp = this.receiveMsg.toString();
            Log.log("消息接收", temp);
            //5.执行客户端发来的消息，并准备反馈客户端
            String recoverMsg = obj.cmd(temp);
            //6.消息反馈
            os.write(recoverMsg.getBytes());
            Log.log("消息发出", recoverMsg);
            //7.关闭
            os.close();
            is.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("服务端接收消息失败");
        }
    }
}

package com.sousou.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 向服务端发送消息的类
 */
public class Client {
    private Socket socket;
    private String severIP;
    private int severPort;
    private OutputStream os;
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;
    private StringBuffer recoverMsg;

    /**
     * 构造方法，构造发送对象
     *
     * @param severIP   服务端IP
     * @param severPort 服务端监听端口
     */
    public Client(String severIP, int severPort) {
        this.severIP = severIP;
        this.severPort = severPort;
    }

    /**
     * 向服务端发送信息，并接收反馈
     *
     * @param msg 发送的消息
     * @return 服务端反馈的消息需要影响执行
     */
    public String send(String msg) {
        try {
            //1.创建连接对象
            this.socket = new Socket(severIP, severPort);

            //2.创建传输对象，创建接收对象
            this.os = socket.getOutputStream();
            this.is = socket.getInputStream();

            //3.输出信息
            this.os.write(msg.getBytes());

            //4.刷新
            this.os.flush();

            //5.关闭输出流，告诉服务端发送完毕
            this.socket.shutdownOutput();

            //6.接收服务端反馈
            this.isr = new InputStreamReader(is);

            //7.缓冲池接收反馈信息
            this.br = new BufferedReader(isr);


            //8.获取反馈信息
            String temp;
            recoverMsg = new StringBuffer();
            do {
                temp = br.readLine();
                if (temp == null) {
                    break;
                } else {
                    this.recoverMsg.append("").append(temp);
                }
            } while (true);

            //9.关闭资源
            this.br.close();
            this.isr.close();
            this.os.close();
            this.is.close();
            this.socket.close();

            //10.处理服务器反馈的信息
            return this.recoverMsg.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("向服务端[" + this.severIP + ":" + this.severPort + "]发送消息失败，消息[" + msg + "]");
            return null;
        }
    }

}

package com.sousou.sever;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义的日志消息类
 *
 * @author kinlon
 * @version 171106
 */
public class Log {
    private static SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    /**
     * 日志输出格式
     *
     * @param type
     * @param info
     */
    public static void log(String type, String info) {
        System.out.println("----------------------------");
        System.out.println(date.format(new Date()) + "\n" + type + "\n" + info);
        System.out.println("----------------------------");
    }

    /**
     * 获取当前时间
     */
    public static String getNow() {
        return date.format(new Date());
    }

}

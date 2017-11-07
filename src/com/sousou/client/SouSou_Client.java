package com.sousou.client;

import java.util.Scanner;

/**
 * 客户端主界面类
 *
 * @author kinlon
 * @version 171106
 */
public class SouSou_Client {
    /**
     * 客户端开启后的第一个主界面
     *
     * @param args
     */
    public static void main(String[] args) {
        Function obj = new Function();
        String str;
        System.out.println("=======欢迎使用 ::嗖嗖移动业务大厅::=======");
        Scanner input = new Scanner(System.in);
        int select;
        //设置换行为默认输入，去除默认空格输入
        do {
            input.useDelimiter("\n");
            System.out.println("1.用户登录\n2.用户注册\n3.资费说明\n4.重置密码\n5.退出系统");
            System.out.print("请选择：");
            str = input.next();
            //使用正则表达式测试输入的是否为正整数，正确返回true
            if (RegexCheck.isPInteger(str)&str.length()<3) {
                select = Integer.parseInt(str);
                if(select<10) {
                    switch (select) {
                        case 1:
                            System.out.println("开始执行------------用户登录------------");
                            obj.login(input);
                            System.out.println("执行完毕------------用户登录------------");
                            break;
                        case 2:
                            System.out.println("开始执行------------用户注册------------");
                            obj.signup(input);
                            System.out.println("执行完毕------------用户注册------------");
                            break;
                        case 3:
                            System.out.println("开始执行------------资费说明------------");
                            obj.comboInfoAll();
                            System.out.println("执行完毕------------资费说明------------");
                            break;
                        case 4:
                            System.out.println("开始执行------------重置密码------------");
                            obj.rePassword(input);
                            System.out.println("执行完毕------------重置密码------------");
                            break;
                        case 5:
                            System.out.println("欢迎下次光临-------::嗖嗖移动业务大厅::-------");
                            System.out.println("嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖口口口口嗖口口口口口口嗖嗖嗖嗖嗖口口口口嗖口口口口口口嗖嗖嗖嗖");
                            System.out.println("嗖口口口口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖口口口口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖口嗖嗖嗖嗖口口口口口口嗖嗖嗖嗖嗖口嗖嗖嗖嗖口口口口口口嗖嗖嗖嗖");
                            System.out.println("嗖口口口口口口口嗖嗖嗖口嗖嗖嗖嗖嗖口口口口口口口嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖口嗖嗖嗖嗖口口口口口口嗖嗖嗖嗖嗖口嗖嗖嗖嗖口口口口口口嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖口口口口口口口嗖嗖嗖口嗖嗖嗖嗖嗖口口口口口口口嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖口嗖嗖嗖口口口口口口口口嗖嗖嗖嗖口嗖嗖嗖口口口口口口口口嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖口嗖嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖口嗖嗖嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖嗖嗖口嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖");
                            System.out.println("嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖");
                            System.exit(0);
                            break;
                        default:
                            System.err.println("[" + str + "]不在选项功能中，请重试");
                            break;
                    }
                }else{
                    System.err.println("[" + str + "]不是正确的选项，请重试");
                }
            } else {
                System.err.println("[" + str + "]不是正确的选项，请重试");
            }
        } while (true);
    }
}

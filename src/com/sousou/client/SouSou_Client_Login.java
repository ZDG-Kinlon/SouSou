package com.sousou.client;

import java.util.Scanner;

/**
 * 二级主界面类
 *
 * @author kinlon
 * @version 171106
 */
public class SouSou_Client_Login {
    private Function obj;
    private String mobileNum;
    private String name;
    private String password;
    private String comboType;
    private String money;
    private String call;
    private String mess;
    private String flow;

    public SouSou_Client_Login() {
        this.obj = new Function();
    }

    /**
     * 二级主界面初始化
     *
     * @param userInfo
     */
    public SouSou_Client_Login(String userInfo) {
        this();
        String[] tempArr = userInfo.split("【分列符】");
        this.mobileNum = tempArr[0];
        this.name = tempArr[1];
        this.password = tempArr[2];
        this.comboType = tempArr[3];
        this.money = tempArr[4];
        this.call = tempArr[5];
        this.mess = tempArr[6];
        this.flow = tempArr[7];
    }

    /**
     * 更新登录界面的个人信息
     *
     * @param mobileNum
     * @param password
     */
    private void update(String mobileNum, String password) {
        //更新信息
        String[] re = obj.upDataUser(mobileNum, password);
        this.name = re[3];
        this.password = re[1];
        this.comboType = re[0];
        this.money = re[5];
        this.call = re[6];
        this.mess = re[7];
        this.flow = re[8];
        if (re[2].equals("0")) {
            System.err.println("服务端：帐号异常掉线，请重新登录");
        }
    }

    /**
     * 登录后的二级主页
     *
     * @param input
     */
    public void main(Scanner input) {
        String str;
        int select;
        do {
            if (this.mobileNum == null | this.password == null) {
                //防止强制登录
                return;
            }
            System.out.println("=======欢迎使用 ::嗖嗖移动业务大厅::=======");
            System.out.println("号码：\t" + this.mobileNum);
            System.out.println("姓名：\t" + this.name);
            System.out.println("套餐：\t" + this.comboType);
            System.out.println("余额(元)：\t" + this.money);
            System.out.println("剩余通话(分钟)：\t" + this.call);
            System.out.println("剩余短信(条)：\t" + this.mess);
            System.out.println("剩余流量(GB)：\t" + this.flow);
            System.out.println("----------------------------------");
            System.out.println("1.本月账单查询\n2使用嗖嗖\n3话费充值\\n4.套餐表更\n5.办理退网\n6.帐号登出");
            System.out.print("请选择：");
            str = input.next();
            if (RegexCheck.isInteger(str)) {
                select = Integer.parseInt(str);
                switch (select) {
                    case 1:
                        System.out.println("开始执行------------本月账单查询------------");
                        obj.getMobileInfo(this.mobileNum, this.password);
                        System.out.println("执行完毕------------本月账单查询------------");
                        break;
                    case 2:
                        System.out.println("开始执行--------------使用嗖嗖-------------");
                        if (obj.useSouSou(input, this.mobileNum, this.password)) {
                            update(this.mobileNum, password);
                        }
                        System.out.println("执行完毕--------------使用嗖嗖-------------");
                        break;
                    case 3:
                        System.out.println("开始执行--------------话费充值-------------");
                        if (obj.addMoney(input, this.mobileNum)) {
                            update(this.mobileNum, password);
                        } else {
                            return;
                        }
                        System.out.println("执行完毕--------------话费充值-------------");
                        break;
                    case 4:
                        System.out.println("开始执行--------------套餐变更-------------");
                        if (obj.changeCombo(input, this.mobileNum, this.password)) {
                            update(this.mobileNum, password);
                        }
                        System.out.println("执行完毕--------------套餐变更-------------");
                        break;
                    case 5:
                        System.out.println("开始执行--------------办理退网-------------");
                        if (obj.mobileStop(input, this.mobileNum, this.password)) {
                            return;
                        }
                        System.out.println("执行完毕--------------办理退网-------------");
                        break;
                    case 6:
                        System.out.println("开始执行--------------帐号登出-------------");
                        obj.logOut(this.mobileNum, this.password);
                        System.out.println("口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口");
                        System.out.println("口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口");
                        System.out.println("口口口口口口口口口口口口口口口口口口口口嗖口口口口口嗖口嗖口口口口口口");
                        System.out.println("口嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖口口口口嗖口口口口口嗖口口嗖口口口口口");
                        System.out.println("口口口口口口口嗖口口口口口口口口口口口嗖口口口口口口嗖口口口口口口口口");
                        System.out.println("口口口口口口口嗖口口口口口口口口口口口嗖口口嗖口口口嗖口嗖嗖嗖口口口口");
                        System.out.println("口口口口口口口嗖口口口口口口口口口口嗖口口口嗖口嗖嗖嗖嗖口口口口口口口");
                        System.out.println("口口口口口口口嗖口口口口口口口口口嗖嗖嗖嗖嗖口口口口嗖口口口口口口口口");
                        System.out.println("口口口口口口口嗖口口嗖口口口口口口口口口嗖口口口口口嗖口嗖嗖嗖嗖口口口");
                        System.out.println("口口口口口口口嗖口口口嗖口口口口口口口嗖口口口嗖嗖嗖嗖嗖口口口口口口口");
                        System.out.println("口口口口口口口嗖口口口口嗖口口口口口嗖口口口口口口口嗖口口口嗖口口口口");
                        System.out.println("口口口口口口口嗖口口口口口嗖口口口嗖嗖嗖嗖嗖嗖口口口嗖口口嗖口口口口口");
                        System.out.println("口口口口口口口嗖口口口口口嗖口口口口嗖口口口口口口口口嗖嗖口口口口口口");
                        System.out.println("口口口口口口口嗖口口口口口口口口口口口口口口口口口口口嗖口口口嗖口口口");
                        System.out.println("口口口口口口口嗖口口口口口口口口口口口口嗖嗖嗖口口口嗖口嗖口口嗖口口口");
                        System.out.println("口口口口口口口嗖口口口口口口口口口嗖嗖嗖口口口口口嗖口口口嗖口嗖口口口");
                        System.out.println("口口口口口口口嗖口口口口口口口口口口嗖口口口口嗖嗖口口口口口嗖嗖口口口");
                        System.out.println("口口口口口口口嗖口口口口口口口口口口口口口口口口口口口口口口口嗖口口口");
                        System.out.println("口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口");
                        System.out.println("口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口口");
                        System.out.println("执行完毕--------------帐号登出-------------");
                        return;
                }
            }
        } while (true);

    }
}

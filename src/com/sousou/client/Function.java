package com.sousou.client;

import java.util.Random;
import java.util.Scanner;

/**
 * 客户端功能类
 */
public class Function {
    private Resolve obj = new Resolve();
    private Client ctt = new Client("localhost", 6661);

    /**
     * 客户端帐号登录-一级主页
     *
     * @param input
     */
    public void login(Scanner input) {
        String mobileNum;
        String password;
        //帐号输入
        System.out.print("请输入手机号：");
        mobileNum = input.next();
        //密码输入
        System.out.print("请输入密码：");
        password = Hash.sha1(input.next());

        //生成向服务端发送的信息字符串（登录识别符："Login"），登录成功返回用户信息
        String userInfo = obj.cmd(ctt.send("Login【参数符】" + mobileNum + "【参数符】" + password));
        if (userInfo != null) {
            //登录成功，开启二级菜单
            System.out.println("口口口口口口口口口口口口口口口口口口口");
            System.out.println("口口口口口口口口口口口口口口口口口口口");
            System.out.println("口口口口口口口口口口嗖口口口口口口口口");
            System.out.println("口口口嗖口口口口口口嗖口口口口口口口口");
            System.out.println("口口口口嗖口嗖嗖嗖嗖嗖嗖嗖嗖嗖口口口口");
            System.out.println("口口口口嗖口口口口口嗖口口口口口口口口");
            System.out.println("口嗖口口口口口嗖嗖嗖嗖嗖嗖嗖口口口口口");
            System.out.println("口口嗖口口口口口口口嗖口口口口口口口口");
            System.out.println("口口嗖口口口嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖口口口");
            System.out.println("口口口口嗖口口口口口口口口口口口口口口");
            System.out.println("口口口口嗖口口嗖嗖嗖嗖嗖嗖嗖口口口口口");
            System.out.println("口口口嗖口口口嗖口口口口口嗖口口口口口");
            System.out.println("口嗖嗖嗖口口口嗖嗖嗖嗖嗖嗖嗖口口口口口");
            System.out.println("口口口嗖口口口嗖口口口口口嗖口口口口口");
            System.out.println("口口口嗖口口口嗖嗖嗖嗖嗖嗖嗖口口口口口");
            System.out.println("口口口嗖口口口嗖口口口口口嗖口口口口口");
            System.out.println("口口口嗖口口口嗖口口口嗖口嗖口口口口口");
            System.out.println("口口口口口口口嗖口口口口嗖口口口口口口");
            System.out.println("口口口口口口口口口口口口口口口口口口口");
            System.out.println("口口口口口口口口口口口口口口口口口口口");
            System.out.println("口口口嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖口口口口口");
            System.out.println("口口口嗖口口口口口口口口口嗖口口口口口");
            System.out.println("口口口嗖口口口口口口口口口嗖口口口口口");
            System.out.println("口口口嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖口口口口口");
            System.out.println("口口口嗖口口嗖口口口口口嗖口口口口口口");
            System.out.println("口口口嗖口口口嗖口口口嗖口口口口口口口");
            System.out.println("口口口嗖口嗖嗖嗖嗖嗖嗖嗖嗖嗖口口口口口");
            System.out.println("口口口嗖口口口嗖口口口嗖口口口口口口口");
            System.out.println("口口口嗖口口口嗖口口口嗖口口口口口口口");
            System.out.println("口口口嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖嗖口口口口");
            System.out.println("口口口嗖口口口嗖口口口嗖口口口口口口口");
            System.out.println("口口嗖口口口口嗖口口口嗖口口口口口口口");
            System.out.println("口口嗖口口口嗖口口口口嗖口口口口口口口");
            System.out.println("口嗖口口口口嗖口口口口嗖口口口口口口口");
            System.out.println("口口口口口嗖口口口口口嗖口口口口口口口");
            System.out.println("口口口口口口口口口口口口口口口口口口口");
            System.out.println("口口口口口口口口口口口口口口口口口口口");
            //开启二级主界面
            SouSou_Client_Login main = new SouSou_Client_Login(userInfo,password);
            main.main(input);
        }
        //没有登录成功
    }

    /**
     * 客户端注册帐号-一级主页
     *
     * @param input
     */
    public void signup(Scanner input) {
        String[][] dataArr;
        int[] rc;
        int r;
        String data;
        String mobileNum = "";
        String password;
        String name;
        String comboType;
        String money;

        //S    1.输入手机号
        data = obj.cmd(ctt.send("GetNoUsedMobile【参数符】9"));//向服务端请求未注册的手机号，请求9个
        dataArr = str2arr(data);
        System.out.println("服务端：以下是还没有注册的手机号");
        rc = strArrPrint(dataArr, ".\t");
        r = rc[0];
        //获取输入信息
        do {
            System.out.print("请选择一个编号，或输入一个手机号：");
            String inputTemp = input.next();
            if (inputTemp.length() < 11) {
                //输入的是编号
                if (RegexCheck.isPInteger(inputTemp)) {
                    int selectNum=Integer.parseInt(inputTemp);
                    if(selectNum<=r & selectNum>0){
                        mobileNum = dataArr[selectNum-1][0];
                        System.out.println("选择的手机号为["+mobileNum+"]");
                        break;
                    }else{
                        System.err.println("输入的编号超出范围，请重试");
                    }
                } else {
                    System.err.println("输入的编号错误，请重试");
                }
            } else {
                //借助正则表达式检测输入的手机号是否正确，规则：139开头11位手机号
                if (RegexCheck.isMatch("139[0-9]{8}", inputTemp)) {
                    mobileNum = inputTemp;
                    break;
                } else {
                    System.err.println("[" + inputTemp + "]不是我们的帐号，请输入任意字符后重试，或输入\"exit\"退出");
                    if (input.next().equals("exit")) {
                        break;
                    }
                }
            }
        } while (true);
        //E    1.输入手机号

        //S    2.输入密码
        do {
            System.out.print("请输入密码【未能实现输入时显示\"*\"】：");
            password = Hash.sha1(input.next());
            System.out.print("请再次输入密码【未能实现输入时显示\"*\"】：");
            if (password.equals(Hash.sha1(input.next()))) {
                break;
            } else {
                System.err.println("两次输入的密码不一致，请输入任意字符后重试，或输入\"exit\"退出");
                if (input.next().equals("exit")) {
                    break;
                }
            }
        } while (true);
        //E    2.输入密码

        //S    3.输入姓名
        System.out.print("请输入姓名（请自行脑补这里输入了身份证并通过了验证）：");
        name = input.next();
        //E    3.输入姓名

        //S    4.选择套餐
        //向服务端请求全部的套餐种类
        data = obj.cmd(ctt.send("GetComboType"));
        dataArr = str2arr(data);
        //输出套餐信息
        System.out.println("编号\t名称\t月租[元/月]\t通话时长[分]\t短信条数[条]\t上网流量[GB]");
        rc = strArrPrint(dataArr, null);
        r = rc[0];
        //开始选择并输入
        do {
            System.out.print("请输入套餐编号：");
            comboType = input.next();
            if (RegexCheck.isPInteger(comboType)) {
                if (r >= Integer.parseInt(comboType)) {
                    break;
                } else {
                    System.err.println("输入的套餐编号有误，请重试");
                }
            } else {
                System.err.println("输入的套餐编号有误，请重试");
            }
        } while (true);
        //E    4.选择套餐

        //S    5.手机充值
        //获取最小月租金额
        String moneyLimit = "0.0";
        for (int n = 0; n < r; n++) {
            if (comboType.equals(dataArr[n][0])) {
                moneyLimit = dataArr[n][2];
                break;
            }
        }
        //开始输入
        do {
            System.out.print("请输入要充值的金额，不得小于" + moneyLimit + "元：");
            money = input.next();
            if (RegexCheck.isDouble(money) | RegexCheck.isInteger(money)) {
                if (Double.parseDouble(moneyLimit) <= Double.parseDouble(money)) {
                    break;
                } else {
                    System.err.println("输入的金额不足，请重试");
                }
            } else {
                System.err.println("输入的金额异常，请重试");
            }
        } while (true);
        //E    5.手机充值

        //生成向服务端发送的信息字符串（注册识别符："Signup"）
        obj.cmd(ctt.send("Signup【参数符】" + mobileNum + "【参数符】" + password + "【参数符】" + name + "【参数符】" + comboType + "【参数符】" + money));
    }


    /**
     * 获取当前系统的所有套餐介绍-一级主页
     */
    public void comboInfoAll() {
        System.out.println("可使用的套餐：\n编号\t名称\t月租(元/月)\t通话时长(分钟)\t短信条数(条)\t上网流量(GB)");
        //向服务端请求全部的套餐种类
        String data = obj.cmd(ctt.send("GetComboType"));
        strArrPrint(str2arr(data), null);
    }

    /**
     * 忘记密码后的重置操作-一级主页
     *
     * @param input
     */
    public void rePassword(Scanner input) {
        String mobileNum;
        String name;
        String password;

        do {
            //帐号输入
            System.out.print("请输入忘记密码手机号：");
            mobileNum = input.next();

            //姓名输入
            System.out.print("请输入手机号注册的姓名(请自行脑补这里有个身份证验证)：");
            name = input.next();

            //生成向服务端发送的信息字符串（重置密码识别符："RePassword"），1验证信息
            if (obj.cmd(ctt.send("RePassword【参数符】1【参数符】" + mobileNum + "【参数符】" + name))==null) {
                System.err.println("请输入任意字符后重试，或输入\"exit\"退出");
                if (input.next().equals("exit")) {
                    break;
                }
            } else {
                //新密码输入
                do {
                    System.out.print("请输入新密码【未能实现输入时显示\"*\"】：");
                    password = Hash.sha1(input.next());
                    System.out.print("请再次输入新密码【未能实现输入时显示\"*\"】：");
                    if (password.equals(Hash.sha1(input.next()))) {
                        break;
                    } else {
                        System.err.println("两次输入的密码不一致，请重试");
                    }
                } while (true);
                //生成向服务端发送的信息字符串（登录识别符："RePassword"），2修改新密码
                if (obj.cmd(ctt.send("RePassword【参数符】2【参数符】" + mobileNum + "【参数符】" + name + "【参数符】" + password)).equals("OK")) {
                    System.out.println("密码重置成功");
                    break;
                } else {
                    System.err.println("服务器：输入的手机号和姓名不正确，请输入任意字符后重试，或输入\"exit\"退出");
                    if (input.next().equals("exit")) {
                        break;
                    }
                }
            }
        } while (true);
    }

    /**
     * 帐号停用-二级主页
     *
     * @param input
     * @param mobileNum
     * @param password
     */
    public boolean mobileStop(Scanner input, String mobileNum, String password) {
        System.err.print("【警告】该操作会删除当前账户，无法恢复，请知悉！\n[请输入帐号" + mobileNum + "绑定的使用者的姓名]：");
        String name = input.next();
        System.out.print("即将执行【删除】帐号[" + mobileNum + "]，请输入\"执行\"\n如果需要放弃，请输入任意字符：");
        if (input.next().equals("执行")) {
            String re=obj.cmd(ctt.send("MobileStop【参数符】" + mobileNum + "【参数符】" + password + "【参数符】" + name));
            if (re==null) {
                return false;
            } else  if(re.equals("OK")){
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * 获取消费记录-二级主页
     *
     * @param mobileNum
     */
    public void getMobileInfo(String mobileNum, String password) {
        //请求数据
        System.out.println("编号\t\t手机号\t\t时间\t\t\t\t类型\t\t说明");
        strArrPrint(str2arr(obj.cmd(ctt.send("MobileLog【参数符】" + mobileNum + "【参数符】" + password))), ".\t\t");
    }

    /**
     * 使用嗖嗖功能-二级主页
     *
     * @param input
     * @param mobileNum
     * @param password
     * @return
     */
    public boolean useSouSou(Scanner input, String mobileNum, String password) {
        //1.构造模拟使用环境
        String[][] emu = new String[][]{
                {"Call", "90", "问候客户，谁知其如此难缠通话90分钟"},
                {"Call", "30", "询问妈妈身体状况本地通话30分钟"},
                {"Mess", "5", "参与环境保护实施方案问卷调查发送短信5条"},
                {"Mess", "50", "通知朋友手机换号，发送短信50条"},
                {"Flow", "1", "和女友微信视频聊天使用流量1G"},
                {"Flow", "2", "晚上手机在线看韩剧，不留神睡着啦！ 使用流量2G"}
        };
        //2.随机选取一个环境useItem，0=使用类型;1=使用量;2=使用描述
        Random rd = new Random();
        int rdSelect = rd.nextInt(emu.length);
        System.out.println(emu[rdSelect][2]);
        String[] useItem = emu[rdSelect];
        //3.发送使用请求
        String re = obj.cmd(ctt.send("Use【参数符】0【参数符】" + mobileNum + "【参数符】" + password + "【参数符】" + useItem[0] + "【参数符】" + useItem[1]));
        if (re == null || re.equals("NoEnvMoney") | re.equals("Password") | re.equals("IsLogin")) {
            //欠费状态异常未使用
            return false;
        } else {
            //可以使用需要提示验证
            System.out.print("是否继续[Y继续/exit放弃]：");
            if (input.next().equals("exit")) {
                return true;
            } else {
                re = obj.cmd(ctt.send("Use【参数符】1【参数符】" + mobileNum + "【参数符】" + password + "【参数符】" + useItem[0] + "【参数符】" + useItem[1]));
                if (re == null) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    /**
     * 变更套餐-二级主页
     *
     * @param input
     * @param mobileNum 当前用户
     */
    public boolean changeCombo(Scanner input, String mobileNum, String password) {
        String[][] comboInfo;
        String comboSelect;
        System.out.print("变更套餐会清空当前套餐的信息，剩余内容不会被新套餐继承，输入\"Y\"继续，其他退出:");
        if (input.next().equals("Y")) {
            //向服务端请求全部的套餐种类，并输出
            comboInfo = str2arr(obj.cmd(ctt.send("GetComboType")));
            int r = strArrPrint(comboInfo, null)[0];
            //开始选择并输入
            do {
                System.out.print("\n请输入套餐编号：");
                comboSelect = input.next();
                if (RegexCheck.isInteger(comboSelect)) {
                    if (r >= Integer.parseInt(comboSelect)) {
                        //向服务器发送消息
                        switch (obj.cmd(ctt.send("ChangeCombo【参数符】" + mobileNum + "【参数符】" + password + "【参数符】" + comboSelect))) {
                            case "Equal":
                                //输入的套餐一致
                                System.err.println("请输入任意字符后重试，或输入\"exit\"退出");
                                if (input.next().equals("exit")) {
                                    break;
                                }
                                break;
                            case "Money":
                                System.err.println("请输入任意字符后重试，或输入\"exit\"退出");
                                if (input.next().equals("exit")) {
                                    break;
                                }
                                break;
                            case "Password":
                                break;
                            case "IsLogin":
                                break;
                            case "OK":
                                System.out.println("套餐变更成功");
                                //执行成功，主页更新
                                return true;
                            default:
                                System.err.println("未知消息");
                                break;
                        }
                    } else {
                        System.err.println("输入的套餐编号有误，请重试");
                    }
                } else {
                    System.err.println("输入的套餐编号有误，请重试");
                }
            } while (true);
        }
        return false;
    }


    /**
     * 让帐号下线
     *
     * @param mobileNum
     */
    public void logOut(String mobileNum, String password) {
        String re=obj.cmd(ctt.send("LogOut【参数符】" + mobileNum + "【参数符】" + password));
        if (re==null) {
            System.err.println("服务端：下线失败");
        }else if(re.equals("OK")){
            System.out.println("服务端：下线成功");
        }
    }

    /**
     * 话费充值-二级主页
     *
     * @param input
     * @param mobileNum
     * @param password
     * @return
     */
    public boolean addMoney(Scanner input, String mobileNum) {
        do {
            //帐号输入
            System.out.print("请输入重置的金额：");
            String moneyStr = input.next();
            if (RegexCheck.isPDouble(moneyStr)|RegexCheck.isPInteger(moneyStr)) {
                System.out.print("即将为帐号[" + mobileNum + "]充值，金额[" + moneyStr + "]，是否继续！\n[继续请输入\"Y\"，其他放弃操作]：");
                if (input.next().equals("Y")) {
                    obj.cmd(ctt.send("AddMoney【参数符】" + mobileNum + "【参数符】" + moneyStr));
                    return true;
                } else {
                    return false;
                }
            } else {
                System.err.println("[" + moneyStr + "]不是正确的金额，请输入任意字符后重试，或输入\"exit\"退出");
                if (input.next().equals("exit")) {
                    return false;
                }
            }
        } while (true);
    }

    /**
     * 二级页面更新登录用户的信息
     *
     * @param mobileNum
     * @return 新套餐名称，密码，登录状态，姓名，套餐编号，余额，时长，短信，流量
     */
    public String[] upDataUser(String mobileNum, String password) {
        String temp=obj.cmd(ctt.send("GetUserInfo【参数符】" + mobileNum + "【参数符】" + password));
        String[] userInfo = str2arr(temp)[0];
        String[][] comboInfo = str2arr(obj.cmd(ctt.send("GetComboType")));
        int comboNum = Integer.parseInt(userInfo[4]);
        String comboName = comboInfo[comboNum-1][1];
        String[] re = new String[9];
        System.arraycopy(userInfo, 0, re, 0, 9);
        re[0] = comboName;
        return re;
    }


    /**
     * 将二维数组全部输出，并返回行数和列数
     *
     * @param strArr
     * @param split1 行号后间隔符号 null表示不需要行号
     * @return 0列数 1列数
     */
    private int[] strArrPrint(String[][] strArr, String split1) {
        if (strArr == null) {
            return new int[]{-1, -1};
        }
        int r = strArr.length;
        int c = strArr[0].length;
        if (split1 != null) {
            int row = 0;
            for (String[] aStrArr : strArr) {
                System.out.print((++row) + split1);
                for (int m = 0; m < c; m++) {
                    if (m == c - 1) {
                        System.out.print(aStrArr[m] + "\n");
                    } else {
                        System.out.print(aStrArr[m] + "\t\t");
                    }
                }
            }
        } else {
            for (String[] aStrArr : strArr) {
                for (int m = 0; m < c; m++) {
                    if (m == c - 1) {
                        System.out.print(aStrArr[m] + "\n");
                    } else {
                        System.out.print(aStrArr[m] + "\t\t");
                    }
                }
            }
        }
        return new int[]{r, c};
    }

    /**
     * 将服务器发回的消息转换为二维数组
     *
     * @param str
     * @return
     */
    private String[][] str2arr(String str) {
        String[] dataArr1 = str.split("【分行符】");
        int dataCount1 = dataArr1.length;
        String[] dataArr2 = dataArr1[0].split("【分列符】");
        int dataCount2 = dataArr2.length;
        String[][] dataArr = new String[dataCount1][dataCount2];
        for (int n = 0; n < dataCount1; n++) {
            dataArr1 = str.split("【分行符】");
            for (int m = 0; m < dataCount2; m++) {
                dataArr2 = dataArr1[n].split("【分列符】");
                dataArr[n][m] = dataArr2[m];
            }
        }
        return dataArr;
    }
}

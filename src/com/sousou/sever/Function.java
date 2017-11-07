package com.sousou.sever;

import java.text.DecimalFormat;

/**
 * 服务端与数据库之间的通信类，SQL语句的集合类
 */
public class Function {
    //创建数据库对象
    private StringBuilder sb;
    private SQL sql;
    private String[][] result;
    private DecimalFormat df = new DecimalFormat("######0.00");

    public Function() {
        //初始化数据库访问
        this.sql = new SQL();
        this.result = null;
        this.sb = new StringBuilder();
    }

    public String[][] getResult() {
        return result;
    }

    public void setResult(String[][] result) {
        this.result = result;
    }

    /**
     * 对客户端的消息进行处理
     */
    public String cmd(String msg) {

        //将空消息当作垃圾处理
        if (msg == null) return null;

        //1.将服务端接收到的消息进行首次处理
        String[] clientMsg = msg.split("【参数符】");

        //2.分析请求的类型，temp[0]，然后根据不同类型进行不同的处理
        //反馈信息与客户端client.Function.cmd()对应
        switch (clientMsg[0]) {
            //帐号登录
            case "Login":
                return Login(clientMsg);

            //帐号注册
            case "Signup":
                return Signup(clientMsg);

            //修改套餐
            case "ChangeCombo":
                return ChangeCombo(clientMsg);

            //使用嗖嗖
            case "Use":
                return Use(clientMsg);

            //余额充值
            case "AddMoney":
                return AddMoney(clientMsg);

            //账户停用
            case "MobileStop":
                return MobileStop(clientMsg);

            //重置密码
            case "RePassword":
                return RePassword(clientMsg);

            //命令专区
            case "LogOut":
                return "Data" + LogOut(clientMsg);
            case "MobileLog":
                return "Data" + MobileLog(clientMsg);
            case "GetNoUsedMobile":
                return "Data" + GetNoUsedMobile(clientMsg);
            case "GetComboType":
                return "Data" + GetComboType();
            case "GetUserInfo":
                return "Data" + GetUserInfo(clientMsg);


            default:
                return null;
        }
    }

    /**
     * 服务端操作-账号删除
     *
     * @param data
     * @return
     */
    private String MobileStop(String[] data) {
        //获取当前帐号的信息
        sql.getMobileByUser(data[1]);
        String[] userInfo = sql.getMobileByUser(data[1])[0];
        //密码验证
        if (!userInfo[1].equals(data[2])) {
            return "MobileStop【参数符】Password";
        }
        //姓名验证
        if (!userInfo[3].equals(data[3])) {
            return "MobileStop【参数符】name";
        }
        //登陆状态验证，操作必须在登陆后下完成
        if (!userInfo[2].equals("0")) {
            return "MobileStop【参数符】IsOffline";
        } else {
            sql.deleteMobile(data[1]);
            return "MobileStop【参数符】OK";
        }
    }

    /**
     * 服务端操作-帐号下线
     *
     * @param data
     * @return
     */
    private String LogOut(String[] data) {
        //获取当前帐号的信息
        sql.getMobileByUser(data[1]);
        String[][] userInfo = sql.getMobileByUser(data[1]);
        //密码验证
        if (!userInfo[0][1].equals(data[2])) {
            return "【参数符】Error【参数符】密码错误";
        }
        //登陆状态验证，操作必须在登陆后下完成
        if (!userInfo[0][2].equals("0")) {
            return "【参数符】Error【参数符】已下线";
        } else {
            sql.setMobileOnOffLine(data[1], false);
            return "【参数符】All【参数符】OK";
        }
    }

    /**
     * 服务端访问-获取手机消费记录数据
     *
     * @param data
     * @return
     */
    private String MobileLog(String[] data) {
        //获取当前帐号的信息
        sql.getMobileByUser(data[1]);
        String[][] userInfo = sql.getMobileByUser(data[1]);
        //密码验证
        if (!userInfo[0][1].equals(data[2])) {
            return "【参数符】Error【参数符】密码错误";
        }
        //登陆状态验证，操作必须在登陆后下完成
        if (!userInfo[0][2].equals("1")) {
            return "【参数符】Error【参数符】未登录";
        }
        return "【参数符】All【参数符】" + strArr2str(sql.getMobileLog(data[1]), "【分列符】", "【分行符】");
    }

    /**
     * 服务端操作-充值
     *
     * @param data
     * @return
     */
    private String AddMoney(String[] data) {
        /*
        帐号存在性判断省去了
         */
        String[] userInfo = sql.getMobileByUser(data[1])[0];
        double money = Double.parseDouble(df.format(Double.parseDouble(userInfo[5])));
        money += Double.parseDouble(data[2]);
        money=round(money);
        sql.setMoney(data[1], String.valueOf(money));
        sql.addMobileLog(data[1], "充值", String.valueOf(money));
        return "AddMoney【参数符】OK【参数符】" + data[1] + "【参数符】" + money;
    }

    /**
     * 服务端操作-修改套餐
     *
     * @param data
     * @return
     */
    private String ChangeCombo(String[] data) {
        //获取当前帐号的信息
        sql.getMobileByUser(data[1]);
        String[][] userInfo = sql.getMobileByUser(data[1]);
        //密码验证
        if (!userInfo[0][1].equals(data[2])) {
            return "ChangeCombo【参数符】Password";
        }
        //登陆状态验证，操作必须在登陆后下完成
        if (!userInfo[0][2].equals("1")) {
            return "ChangeCombo【参数符】IsLogin";
        }
        //套餐验证
        if (userInfo[0][4].equals(data[3])) {
            return "ChangeCombo【参数符】Equal";
        } else {
            //获取用户选择的套餐信息
            String[][] comboInfo = sql.getCombo(data[3]);
            //检查用户的余额是否足够支付新套餐
            double money = Double.parseDouble(userInfo[0][5]) - Double.parseDouble(comboInfo[0][2]);
            money=round(money);
            if (money < 0) {
                return "ChangeCombo【参数符】Money";
            }
            //执行支付套餐操作，余额减少，套餐剩余信息重置
            sql.setUserCombo(data[1], data[3], String.valueOf(money), comboInfo[0][3], comboInfo[0][4], comboInfo[0][5]);
            //消费记录表中添加信息
            sql.addMobileLog(data[1], "月租", comboInfo[0][1] + ";扣费" + String.valueOf(money));
        }
        return null;
    }

    /**
     * 数据库访问-获取用户信息
     *
     * @param data
     * @return
     */
    private String GetUserInfo(String[] data) {
        //获取用户信息
        String[][] userInfo = sql.getMobileByUser(data[1]);
        //密码验证
        if (!userInfo[0][1].equals(data[2])) {
            return "【参数符】Error【参数符】无权限访问用户的注册数据";
        } else {
            return "【参数符】All【参数符】" + strArr2str(userInfo, "【分列符】", "【分行符】");
        }
    }

    /**
     * 服务端操作-帐号登录
     *
     * @param data
     * @return
     */
    private String Login(String[] data) {
        //1.检测帐号是否存在，获取帐号仓库关于这个手机号的记录
        setResult(sql.getMobileByHouse(data[1]));
        if (result == null) {
            return "Login【参数符】NoHave";
        }
        //2.检测是否未被注册
        if (result[0][1].equals("0")) {
            return "Login【参数符】NoSignup";
        }
        //帐号存在，读取user表的数据
        setResult(sql.getMobileByUser(data[1]));

        //3.检测密码是否正确
        if (!(getResult()[0][1].equals(data[2]))) {
            //不正确
            return "Login【参数符】PasswordError";
        }
        //4.检测是否已经登陆，如果已经登录，强制顶下线
        if (getResult()[0][2].equals("1")) {
            //已经登录时
            sql.setMobileOnOffLine(data[1], false);
            return "Login【参数符】IsLogin";
        } else {
            //未登录时
            sql.setMobileOnOffLine(data[1], true);
            /*
            完成登录，配置返回数据
             */
            String name = getResult()[0][3];
            String comboTypeNum = getResult()[0][4];
            String money = getResult()[0][5];
            String call = getResult()[0][6];
            String mess = getResult()[0][7];
            String flow = getResult()[0][8];
            //获取套餐名称
            setResult(sql.getCombo(getResult()[0][4]));
            return "Login【参数符】OK【参数符】" + data[1] + "【分列符】" + name + "【分列符】" + comboTypeNum +
                    "【分列符】" + getResult()[0][1] + "【分列符】" + money + "【分列符】" + call + "【分列符】" + mess +
                    "【分列符】" + flow;
        }

    }


    /**
     * 服务端操作-注册帐号
     *
     * @param data
     * @return
     */
    private String Signup(String[] data) {
        //1.检测帐号存在
        setResult(sql.getMobileByHouse(data[1]));
        if (getResult() == null) {
            return "Signup【参数符】NoHave";
        }
        //2.检测帐号是否被已经注册
        if (getResult()[0][1].equals("1")) {
            return "Signup【参数符】IsUsed";
        }
        //3.修改帐号为已经注册状态
        sql.setMobileUse(data[1], true);
        //4.在user表添加信息 帐号，密码，姓名，套餐，余额
        sql.addUser(data[1], data[2], data[3], data[4], data[5]);
        //5.给帐号重置套餐

        //获取用户选择的套餐信息
        String[] comboInfo = sql.getCombo(data[4])[0];
        //检查用户的余额是否足够支付新套餐
        double money = Double.parseDouble(data[5]) - Double.parseDouble(comboInfo[2]);
        money=round(money);
        if (money < 0) {
            return "ChangeCombo【参数符】Money";
        }
        //执行支付套餐操作，余额减少，套餐剩余信息重置
        sql.setUserCombo(data[1], data[4], String.valueOf(money), comboInfo[3], comboInfo[4], comboInfo[5]);
        //消费记录表中添加信息
        sql.addMobileLog(data[1], "月租", comboInfo[1]);
        sql.addMobileLog(data[1], "余额", String.valueOf(money));

        //6.反馈消息
        return "Signup【参数符】OK";
    }

    /**
     * 服务端操作-重置密码
     *
     * @param data
     * @return
     */
    private String RePassword(String[] data) {
        switch (data[1]) {
            case "1":
                //验证帐号是否存在
                setResult(sql.getMobileByHouse(data[2]));
                if (getResult() == null) {
                    return "RePassword【参数符】NoHave";
                }
                //检测帐号是否被已经注册
                if (getResult()[0][1].equals("0")) {
                    return "RePassword【参数符】NoUsed";
                }
                setResult(sql.getMobileByUser(data[2]));
                //登陆状态验证，操作必须在离线状态完成
                if (!getResult()[0][2].equals("0")) {
                    sql.setMobileOnOffLine(data[2], false);
                }
                //验证姓名和手机号匹配
                setResult(sql.getMobileByUser(data[2]));
                if (getResult()[0][3].equals(data[3])) {
                    return "RePassword【参数符】YES";
                } else {
                    return "RePassword【参数符】NO";
                }
            case "2":
                //验证帐号是否存在
                setResult(sql.getMobileByHouse(data[2]));
                if (getResult() == null) {
                    return "RePassword【参数符】NoHave";
                }
                //检测帐号是否被已经注册
                if (getResult()[0][1].equals("0")) {
                    return "RePassword【参数符】NoUsed";
                }
                setResult(sql.getMobileByUser(data[2]));
                //登陆状态验证，操作必须在离线状态完成
                if (!getResult()[0][2].equals("0")) {
                    sql.setMobileOnOffLine(data[2], false);
                }
                //验证姓名和手机号匹配
                setResult(sql.getMobileByUser(data[2]));
                if (getResult()[0][3].equals(data[3])) {
                    //设置新密码
                    sql.setMobilePassword(data[2], data[4]);
                    return "RePassword【参数符】YES";
                } else {
                    return "RePassword【参数符】NO";
                }
        }
        return null;
    }

    /**
     * 数据库访问-获取还未被注册的号码，随机获取
     *
     * @param data
     * @return
     */
    private String GetNoUsedMobile(String[] data) {
        if (RegexCheck.isInteger(data[1])) {
            //获取还没有使用的帐号，随机获取
            setResult(sql.getMobileNoUsed(data[1]));
            //拼接获取到的帐号
            return "【参数符】All【参数符】" + strArr2str(getResult(), "【分行符】", null);
        } else {
            return "【参数符】Error【参数符】Err:count";
        }
    }

    /**
     * 服务端操作-使用嗖嗖
     *
     * @param data
     * @return
     */
    private String Use(String[] data) {
        //获取需要的数据表
        String[] userInfo = sql.getMobileByUser(data[2])[0];
        String[] comboInfo = sql.getCombo(userInfo[4])[0];
        String[] overMoney = sql.getComboOverMoney()[0];//获取超出的收费标准

        //获取用户的消费信息
        double money = Double.parseDouble(userInfo[5]);
        int userCall = Integer.parseInt(userInfo[6]);
        int userMess = Integer.parseInt(userInfo[7]);
        int userFlow = Integer.parseInt(userInfo[8]);
        int used = Integer.parseInt(data[5]);

        switch (data[1]) {
            case "0"://只使用套餐
                //密码验证
                if (!userInfo[1].equals(data[3])) {
                    return "Use【参数符】Password";
                }
                //登陆状态验证，操作必须在登陆后下完成
                if (!userInfo[2].equals("1")) {
                    return "Use【参数符】IsLogin";
                }
                //套餐验证，是否具备使用的功能
                switch (data[4]) {
                    case "Call":
                        if ("0".equals(comboInfo[3])) {
                            //套餐中没有通话
                            return "Use【参数符】NoUseCall";
                        } else {
                            //验证套餐剩余通话是否充足
                            userCall -= used;
                            if (userCall < 0) {
                                //套餐中通话不足，要使用超出收费
                                return "Use【参数符】NoEnvCall【参数符】";
                            } else {
                                //套餐剩余通话充足，执行SQL
                                sql.setCall(data[2], String.valueOf(userCall));
                                sql.addMobileLog(data[2], "通话", String.valueOf(used));
                                //返回最新通话剩余
                                return "Use【参数符】OK【参数符】Call【参数符】" + userCall;
                            }
                        }
                    case "Mess":
                        if ("0".equals(comboInfo[5])) {
                            //套餐中没有短信
                            return "Use【参数符】NoUseMess";
                        } else {
                            //验证套餐剩余短信是否充足
                            userMess -= used;
                            if (userMess < 0) {
                                //套餐中短信不足
                                return "Use【参数符】NoEnvMess";
                            } else {
                                //套餐剩余通话充足，执行SQL
                                sql.setMess(data[2], String.valueOf(userMess));
                                sql.addMobileLog(data[2], "短信", String.valueOf(used));
                                //返回最新短信剩余
                                return "Use【参数符】OK【参数符】Mess【参数符】" + userMess;
                            }
                        }
                    case "Flow":
                        if ("0".equals(comboInfo[4])) {
                            //套餐中没有流量
                            return "Use【参数符】NoUseMess";
                        } else {
                            //验证套餐剩余流量是否充足
                            userFlow -= used;
                            if (userFlow < 0) {
                                //套餐中短信不足
                                return "Use【参数符】NoEnvFlow";
                            } else {
                                //套餐剩余流量充足，执行SQL
                                sql.setFlow(data[2], String.valueOf(userFlow));
                                sql.addMobileLog(data[2], "流量", String.valueOf(used));
                                //返回最新流量剩余
                                return "Use【参数符】OK【参数符】Flow【参数符】" + userFlow;
                            }
                        }
                    default:
                        break;
                }
                break;
            case "1"://强制消费
                //密码验证
                if (!userInfo[1].equals(data[3])) {
                    return "Use【参数符】Password";
                }
                //登陆状态验证，操作必须在登陆后下完成
                if (!userInfo[2].equals("1")) {
                    return "Use【参数符】IsLogin";
                }
                //套餐验证，是否具备使用的功能
                switch (data[4]) {
                    case "Call":
                        if ("0".equals(comboInfo[3])) {
                            //套餐中没有通话，检查余额
                            money -= used * Double.parseDouble(overMoney[0]);
                            money=round(money);
                            if (money < 0) {
                                //余额不足
                                return "Use【参数符】NoEnvMoney【参数符】" + (0.0 - money);
                            } else {
                                //用户剩余话费充足，执行SQL
                                sql.setMoney(data[2], String.valueOf(money));
                                sql.addMobileLog(data[2], "通话", String.valueOf(used));
                                sql.addMobileLog(data[2], "余额", String.valueOf(money));
                                //返回最新余额
                                return "Use【参数符】OK【参数符】Money【参数符】" + money;
                            }
                        } else {
                            //验证套餐剩余通话是否充足
                            userCall -= used;
                            if (userCall < 0) {
                                //套餐中通话不足，要使用超出收费，检查余额
                                money -= (0 - userCall) * Double.parseDouble(overMoney[0]);
                                money=round(money);
                                if (money < 0) {
                                    //余额不足，返回缺少多少钱
                                    return "Use【参数符】NoEnvMoney【参数符】";
                                } else {
                                    //用户剩余话费充足，执行SQL
                                    sql.setCall(data[2], "0");
                                    sql.setMoney(data[2], String.valueOf(money));
                                    sql.addMobileLog(data[2], "通话", String.valueOf(used));
                                    sql.addMobileLog(data[2], "余额", String.valueOf(money));
                                    //返回最新余额
                                    return "Use【参数符】OK【参数符】Money【参数符】" + money;
                                }
                            } else {
                                //套餐剩余通话充足，执行SQL
                                sql.setCall(data[2], String.valueOf(userCall));
                                sql.addMobileLog(data[2], "通话", String.valueOf(used));
                                return "Use【参数符】OK【参数符】" + userCall;
                            }
                        }
                    case "Mess":
                        if ("0".equals(comboInfo[4])) {
                            //套餐中没有短信，检查余额
                            money -= used * Double.parseDouble(overMoney[1]);
                            money=round(money);
                            if (money < 0) {
                                //余额不足，返回缺少多少钱
                                return "Use【参数符】NoEnvMoney【参数符】" + (0.0 - money);
                            } else {
                                //用户剩余话费充足，执行SQL
                                sql.setMoney(data[2], String.valueOf(money));
                                sql.addMobileLog(data[2], "短信", String.valueOf(used));
                                sql.addMobileLog(data[2], "余额", String.valueOf(money));
                                //返回最新余额
                                return "Use【参数符】OK【参数符】Money【参数符】" + money;
                            }
                        } else {
                            //验证套餐剩余通话是否充足
                            userMess -= used;
                            if (userMess < 0) {
                                //套餐中短信不足，要使用超出收费，检查余额
                                money -= (0 - userMess) * Double.parseDouble(overMoney[1]);
                                money=round(money);
                                if (money < 0) {
                                    //余额不足，返回缺少多少钱
                                    return "Use【参数符】NoEnvMoney【参数符】" + (0.0 - money);
                                } else {
                                    //用户剩余话费充足，执行SQL
                                    sql.setMess(data[2], "0");
                                    sql.setMoney(data[2], String.valueOf(money));
                                    sql.addMobileLog(data[2], "短信", String.valueOf(used));
                                    sql.addMobileLog(data[2], "余额", String.valueOf(money));
                                    //返回最新余额
                                    return "Use【参数符】OK【参数符】Money【参数符】" + money;
                                }
                            } else {
                                //套餐剩余通话充足，执行SQL
                                sql.setMess(data[2], String.valueOf(userMess));
                                sql.addMobileLog(data[2], "短信", String.valueOf(used));
                                return "Use【参数符】OK【参数符】" + userMess;
                            }
                        }
                    case "Flow":
                        if ("0".equals(comboInfo[5])) {
                            //套餐中没有流量，检查余额
                            money -= used * Double.parseDouble(overMoney[2]);
                            money=round(money);
                            if (money < 0) {
                                //余额不足，返回缺少多少钱
                                return "Use【参数符】NoEnvMoney【参数符】" + (0.0 - money);
                            } else {
                                //用户剩余话费充足，执行SQL
                                sql.setMoney(data[2], String.valueOf(money));
                                sql.addMobileLog(data[2], "流量", String.valueOf(used));
                                sql.addMobileLog(data[2], "余额", String.valueOf(money));
                                //返回最新余额
                                return "Use【参数符】OK【参数符】Money【参数符】" + money;
                            }
                        } else {
                            //验证套餐剩余通话是否充足
                            userFlow -= used;
                            if (userFlow < 0) {
                                //套餐中短信不足，要使用超出收费，检查余额
                                money -= (0 - userFlow) * Double.parseDouble(overMoney[2]);
                                money=round(money);
                                if (money < 0) {
                                    //余额不足，返回缺少多少钱
                                    return "Use【参数符】NoEnvMoney【参数符】" + (0.0 - money);
                                } else {
                                    //用户剩余话费充足，执行SQL
                                    sql.setFlow(data[2], "0");
                                    sql.setMoney(data[2], String.valueOf(money));
                                    sql.addMobileLog(data[2], "流量", String.valueOf(used));
                                    sql.addMobileLog(data[2], "余额", String.valueOf(money));
                                    //返回最新余额
                                    return "Use【参数符】OK【参数符】Money【参数符】" + money;
                                }
                            } else {
                                //套餐剩余通话充足，执行SQL
                                sql.setFlow(data[2], String.valueOf(userFlow));
                                sql.addMobileLog(data[2], "流量", String.valueOf(used));
                                return "Use【参数符】OK【参数符】" + userMess;
                            }
                        }
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * 数据库访问-获取全部套餐和月租
     *
     * @return
     */
    private String GetComboType() {
        //获取所有套餐的名称
        setResult(sql.getCombo(null));
        return "【参数符】All【参数符】" + strArr2str(getResult(), "【分列符】", "【分行符】");
    }


    /**
     * 二维字符串数组转换为字符串
     *
     * @param strArr 二位字符串数组
     * @param a1     行间隔符
     * @param a2     列间隔符 null空行
     * @return 字符串
     */
    private String strArr2str(String[][] strArr, String a1, String a2) {
        StringBuilder sb = new StringBuilder();
        if (a2 == null) {
            //单列
            int count = strArr.length;
            for (int n = 0; n < count; n++) {
                if (n == count - 1) {
                    sb.append(strArr[n][0]);
                } else {
                    sb.append(strArr[n][0]);
                    sb.append(a1);
                }
            }
        } else {
            //多列
            int count = strArr.length;
            int count2 = strArr[0].length;
            for (int n = 0; n < count; n++) {
                if (n == count - 1) {
                    for (int m = 0; m < count2; m++) {
                        if (m == count2 - 1) {
                            sb.append(strArr[n][m]);
                        } else {
                            sb.append(strArr[n][m]);
                            sb.append(a1);
                        }
                    }
                } else {
                    for (int m = 0; m < count2; m++) {
                        if (m == count2 - 1) {
                            sb.append(strArr[n][m]);
                            sb.append(a2);
                        } else {
                            sb.append(strArr[n][m]);
                            sb.append(a1);
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * 保留两位小数
     * @param num 小数
     * @return 四舍五入后的小数
     */
    private double round(double num){
        return (Math.round(num*100.0+0.000000000000001))/100.0;
    }

}

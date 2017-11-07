package com.sousou.client;

/**
 * 处理服务端反馈回的信息，并做出解释
 *
 * @author kinlon
 * @version 171106
 *
 */
public class Resolve {
    /**
     * 执行服务器反馈的消息分类目录
     *
     * @param str 服务端消息
     */
    public String cmd(String str) {
        if (str == null) return null;
        //1.将服务端接收到的消息进行首次处理
        String[] severMsg = str.split("【参数符】");
        switch (severMsg[0]) {
            case "Login":
                return Login(severMsg);
            case "Signup":
                Signup(severMsg);
                break;
            case "RePassword":
                return RePassword(severMsg);
            case "ChangeCombo":
                return ChangeCombo(severMsg);
            case "AddMoney":
                AddMoney(severMsg);
            case "Use":
                return Use(severMsg);
            case "Data":
                return Data(severMsg);
            case "GetUserInfo":
                return Data(severMsg);
            case "MobileStop":
                return MobileStop(severMsg);
            default:
                System.err.println("服务端：数据库异常");
                break;
        }
        return null;
    }

    /**
     * 消息反馈处理-帐号删除
     *
     * @param data
     * @return
     */
    private String MobileStop(String[] data) {
        switch (data[1]) {
            case "Password":
                System.err.print("服务端：密码错误");
                return null;
            case "name":
                System.err.print("服务端：姓名错误");
                return null;
            case "IsOffline":
                System.err.print("服务端：帐号掉线");
                return null;
            case "OK":
                System.out.print("服务端：帐号删除执行成功");
                return "OK";
            default:
                return null;
        }
    }

    /**
     * 消息反馈处理-充值
     *
     * @param data
     * @return
     */
    private String AddMoney(String[] data) {
        switch (data[1]) {
            case "OK":
                System.out.println("服务端：充值成功");
                return "OK";
            default:
                break;
        }
        return null;
    }

    /**
     * 消息反馈处理-使用嗖嗖
     *
     * @param data
     * @return
     */
    private String Use(String[] data) {
        switch (data[1]) {
            case "OK":
                System.out.println("服务端：执行成功");
                switch (data[2]) {
                    case "Call":
                        System.out.println("服务端：当前套餐中的【通话时长】还有：" + data[3]);
                        return "Call";
                    case "Mess":
                        System.out.println("服务端：当前套餐中的【短信条数】还有：" + data[3]);
                        return "Mess";
                    case "Flow":
                        System.out.println("服务端：当前套餐中的【剩余流量】还有：" + data[3]);
                        return "Flow";
                    case "Money":
                        System.out.println("服务端：当前套餐中的【剩余金额】还有：" + data[3]);
                        return "Money";
                    default:
                        return null;
                }
            case "Password":
                System.err.println("服务端：密码验证错误");
                break;
            case "IsLogin":
                System.err.println("服务端：账户未登录");
                break;
            case "NoUseCall":
                System.out.println("服务端：套餐中没有通话，如果继续使用会按照正常标准扣费");
                return "NoUseCall";
            case "NoEnvCall":
                System.out.println("服务端：套餐中剩余通话不足，如果继续使用，超出的部分会按照正常标准扣费");
                return "NoEnvCall";

            case "NoUseMess":
                System.out.println("服务端：套餐中没有短信，如果继续使用会按照正常标准扣费");
                return "NoUseMess";
            case "NoEnvMess":
                System.out.println("服务端：套餐中剩余短信不足，如果继续使用，超出的部分会按照正常标准扣费");
                return "NoEnvMess";

            case "NoUseFlow":
                System.out.println("服务端：套餐中没有流量，如果继续使用会按照正常标准扣费");
                return "NoUseFlow";
            case "NoEnvFlow":
                System.out.println("服务端：套餐中剩余流量不足，如果继续使用，超出的部分会按照正常标准扣费");
                return "NoEnvFlow";

            case "NoEnvMoney":
                System.out.println("服务端：用户余额不足，请充值");
                return "NoEnvMoney";
            default:
                break;
        }
        return null;
    }

    /**
     * 消息反馈处理-登录
     *
     * @param data
     * @return
     */
    private String Login(String[] data) {
        switch (data[1]) {
            case "OK":
                System.out.println("服务端：登录成功");
                return data[2];
            case "PasswordError":
                System.err.println("服务端：密码不正确");
            case "NoHave":
                System.err.println("服务端：帐号不存在");
            case "IsUsed":
                System.err.println("服务端：帐号已经被注册");
            case "IsLogin":
                System.err.println("服务端：帐号已经登录，现已经被强制下线");
            default:
                System.err.println("服务端：未知错误");
                break;
        }
        return null;
    }

    /**
     * 消息反馈处理-注册
     *
     * @param data
     */
    private void Signup(String[] data) {
        switch (data[1]) {
            case "OK":
                System.out.println("服务端：注册成功");
                break;
            case "IsUsed":
                System.out.println("服务端：手机号已经被注册");
                break;
            case "NoHave":
                System.out.println("服务端：数据库没有这个手机号，可能是不对外发放的特殊手机号");
                break;
            default:
                System.out.println("服务端：数据库注册时异常，请联系数据库管理员");
                break;
        }
    }

    /**
     * 消息反馈处理-数据控制
     *
     * @param data
     * @return
     */
    private String Data(String[] data) {
        switch (data[1]) {
            case "All":
                return data[2];
            case "Error":
                System.err.println(data[2]);
                break;
            default:
                System.err.println("服务端：未知的输出信息");
                break;
        }
        return null;

    }

    /**
     * 消息反馈处理-重置密码
     *
     * @param data
     * @return
     */
    private String RePassword(String[] data) {
        switch (data[1]) {
            case "NoHave":
                System.err.println("服务端：该手机号不存在");
                break;
            case "NoUsed":
                System.err.println("服务端：该手机号没有被注册");
                break;
            case "NO":
                System.err.println("服务端：输入的手机号和姓名不匹配");
                break;
            case "YES":
                System.err.println("服务端：验证正确");
                return "OK";
            case "IsLogin":
                System.err.println("服务端：帐号已经登录，请下线该帐号");
                break;
            default:
                System.err.println("服务端：未知的输出信息");
                break;
        }
        return null;
    }

    /**
     * 消息反馈处理-修改套餐
     *
     * @param data
     * @return
     */
    private String ChangeCombo(String[] data) {
        switch (data[1]) {
            case "Equal":
                System.err.println("服务端：输入的新套餐和现有套餐一致，未执行修改");
                return "Equal";
            case "Password":
                System.err.println("服务端：密码验证错误，未执行修改，请重新登录");
                return "Password";
            case "Money":
                System.err.println("服务端：余额不足，未执行修改");
                return "Money";
            case "OK":
                System.out.println("服务端：修改成功");
                return "OK";
            case "IsLogin":
                System.err.println("服务端：修改套餐需要在帐号上线才能执行");
                return "IsLogin";
            default:
                System.err.println("服务端：未知异常");
                break;
        }
        return null;
    }
}

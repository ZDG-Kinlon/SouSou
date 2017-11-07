package com.sousou.sever;

/**
 * 服务端操作数据库的SQL语句类
 *
 * @author kinlon
 * @version 171106
 */
public class SQL {
    private DataBase db;

    public SQL() {
        //2.连接数据库，并开启SQL命令执行环境
        db = new DataBase("jdbc:mysql://localhost:3306/sousou", "kinlon", "gdadmin");
        db.contect();
        db.statement();
    }

    public String[][] getMobileLog(String mobileNum) {
        db.runSQL("" +
                "SELECT\n" +
                "mobile_log.MOBILE_NUM,\n" +
                "mobile_log.DATETIME,\n" +
                "mobile_log.TYPE,\n" +
                "mobile_log.INFOMATION\n" +
                "FROM\n" +
                "mobile_log\n" +
                "WHERE\n" +
                "mobile_log.MOBILE_NUM = " + mobileNum
        );
        return db.getResult();
    }

    /**
     * 数据库-查询-获取还没有被注册的手机号，随机获取
     *
     * @param count 请求数量
     * @return 手机号数据
     */
    public String[][] getMobileNoUsed(String count) {
        db.runSQL("" +
                "SELECT mobile_warehouse.MOBILE_NUM\n" +
                "FROM mobile_warehouse\n" +
                "WHERE mobile_warehouse.IS_USED = 0\n" +
                "ORDER BY RAND()\n" +
                "LIMIT " + count
        );
        return db.getResult();
    }

    /**
     * 数据库-查询-获取手机号在帐号仓库[mobile_warehouse]的数据
     *
     * @param mobileNum 手机号
     * @return null表示不存在
     */
    public String[][] getMobileByHouse(String mobileNum) {
        db.runSQL("" +
                "SELECT\n" +
                "mobile_warehouse.MOBILE_NUM,\n" +
                "mobile_warehouse.IS_USED\n" +
                "FROM\n" +
                "mobile_warehouse\n" +
                "WHERE\n" +
                "mobile_warehouse.MOBILE_NUM = " + mobileNum
        );
        return db.getResult();
    }

    /**
     * 数据库-查询-获取手机号在[user]的注册信息
     *
     * @param mobileNum 手机号
     * @return 注册的详细信息
     */
    public String[][] getMobileByUser(String mobileNum) {
        db.runSQL("" +
                "SELECT\n" +
                "user.MOBILE_NUM,\n" +
                "user.`PASSWORD`,\n" +
                "user.IS_LOGIN,\n" +
                "user.`NAME`,\n" +
                "user.COBOM_TYPE,\n" +
                "user.MONEY,\n" +
                "user.USECALL,\n" +
                "user.USEMESS,\n" +
                "user.USEFLOW\n" +
                "FROM\n" +
                "user\n" +
                "WHERE\n" +
                "user.MOBILE_NUM = " + mobileNum
        );
        return db.getResult();
    }

    /**
     * 数据库-查询-获取套餐信息表[cobom_set]的数据
     *
     * @param comboNum 套餐编号，null为全部获取
     * @return
     */
    public String[][] getCombo(String comboNum) {
        if (comboNum == null) {
            db.runSQL("" +
                    "SELECT\n" +
                    "cobom_set.ID,\n" +
                    "cobom_set.`NAME`,\n" +
                    "cobom_set.RENT,\n" +
                    "cobom_set.`CALL`,\n" +
                    "cobom_set.MESS,\n" +
                    "cobom_set.FLOW\n" +
                    "FROM\n" +
                    "cobom_set"
            );
        } else {
            db.runSQL("" +
                    "SELECT\n" +
                    "cobom_set.ID,\n" +
                    "cobom_set.`NAME`,\n" +
                    "cobom_set.RENT,\n" +
                    "cobom_set.`CALL`,\n" +
                    "cobom_set.MESS,\n" +
                    "cobom_set.FLOW\n" +
                    "FROM\n" +
                    "cobom_set\n" +
                    "WHERE\n" +
                    "cobom_set.ID = " + comboNum
            );
        }
        return db.getResult();
    }

    /**
     * 获取超出套餐后的收费标准
     *
     * @return
     */
    public String[][] getComboOverMoney() {
        db.runSQL("" +
                "SELECT\n" +
                "cobom_overset.`CALL`,\n" +
                "cobom_overset.MESS,\n" +
                "cobom_overset.FLOW\n" +
                "FROM\n" +
                "cobom_overset\n"
        );
        return db.getResult();
    }


    /**
     * 数据库-操作-设置通话时长
     *
     * @param mobileNum
     * @param used
     */
    public void setCall(String mobileNum, String newValue) {
        db.runSQL("" +
                "UPDATE\n" +
                "user\n" +
                "SET\n" +
                "user.USECALL = " + newValue + ",\n" +
                "WHERE\n" +
                "user.MOBILE_NUM = " + mobileNum
        );
    }

    /**
     * 数据库-操作-设置短信条数
     *
     * @param mobileNum
     * @param used
     */
    public void setMess(String mobileNum, String newValue) {
        db.runSQL("" +
                "UPDATE\n" +
                "user\n" +
                "SET\n" +
                "user.USEMESS = " + newValue + ",\n" +
                "WHERE\n" +
                "user.MOBILE_NUM = " + mobileNum
        );
    }

    /**
     * 数据库-操作-设置套餐流量
     *
     * @param mobileNum
     * @param used
     */
    public void setFlow(String mobileNum, String newValue) {
        db.runSQL("" +
                "UPDATE\n" +
                "user\n" +
                "SET\n" +
                "user.USEFLOW = " + newValue + ",\n" +
                "WHERE\n" +
                "user.MOBILE_NUM = " + mobileNum
        );
    }

    /**
     * 数据库-操作-设置账户余额
     *
     * @param mobileNum
     * @param used
     */
    public void setMoney(String mobileNum, String newValue) {
        db.runSQL("" +
                "UPDATE\n" +
                "user\n" +
                "SET\n" +
                "user.MONEY = " + newValue + ",\n" +
                "WHERE\n" +
                "user.MOBILE_NUM = " + mobileNum
        );
    }

    /**
     * 数据库-操作-添加消费记录到[mobile_log]表
     *
     * @param mobileNum 手机号
     * @param type      消费类型
     * @param info      消费信息
     */
    public void addMobileLog(String mobileNum, String type, String info) {
        db.runSQL("" +
                "INSERT INTO \n" +
                "mobile_log \n" +
                "VALUES \n" +
                "( \"" + mobileNum + "\",\"" + Log.getNow() + "\"" + type + "\"" + info + "\")"
        );
    }

    /**
     * 数据库-操作-添加新注册的用户信息到[user]表
     *
     * @param mobileNum 手机号
     * @param password  密码
     * @param name      姓名
     * @param comboNum  套餐类型编号
     * @param money     余额
     */
    public void addUser(String mobileNum, String password, String name, String comboNum, String money) {
        db.runSQL("" +
                "INSERT INTO \n" +
                "USER \n" +
                "VALUES \n" +
                "( \"" + mobileNum + "\", \"" + password + "\", 0, \"" + name + "\", " + comboNum + ", " + money + " , 0, 0, 0,)"
        );
    }


    /**
     * 数据库-操作-删除注册帐号
     *
     * @param mobileNum
     */
    public void deleteMobile(String mobileNum) {
        //消费记录删除
        db.runSQL("" +
                "DELETE \n" +
                "FROM\n" +
                "mobile_log \n" +
                "WHERE\n" +
                "mobile_log.MOBILE_NUM = " + mobileNum
        );

        //用户信息表删除
        db.runSQL("" +
                "DELETE \n" +
                "FROM\n" +
                "USER \n" +
                "WHERE\n" +
                "USER.MOBILE_NUM = " + mobileNum
        );
        //重置帐号仓库
        db.runSQL("" +
                "UPDATE mobile_warehouse \n" +
                "SET mobile_warehouse.IS_USED = 0 \n" +
                "WHERE\n" +
                "mobile_warehouse.MOBILE_NUM = " + mobileNum

        );
    }

    /**
     * 数据库-操作-修改[user]表的金额和套餐信息
     */
    public void setUserCombo(String mobileNum, String comboNum, String money, String call, String mess, String flow) {
        db.runSQL("" +
                "UPDATE\n" +
                "user\n" +
                "SET\n" +
                "user.COBOM_TYPE = " + comboNum + ",\n" +
                "user.MONEY = " + money + ",\n" +
                "user.USECALL = " + call + ",\n" +
                "user.USEMESS = " + mess + ",\n" +
                "user.USEFLOW = " + flow + "\n" +
                "WHERE\n" +
                "user.MOBILE_NUM = " + mobileNum
        );
    }

    /**
     * /**
     * 数据库-操作-修改手机号登录离线状态
     *
     * @param mobileNum 手机号
     * @param isOnline  在线true
     */
    public void setMobileOnOffLine(String mobileNum, boolean isOnline) {
        if (isOnline) {
            //上线
            db.runSQL("" +
                    "UPDATE\n" +
                    "user\n" +
                    "SET\n" +
                    "user.IS_LOGIN = 1\n" +
                    "WHERE\n" +
                    "user.MOBILE_NUM = " + mobileNum
            );
        } else {
            //下线
            db.runSQL("" +
                    "UPDATE\n" +
                    "user\n" +
                    "SET\n" +
                    "user.IS_LOGIN = 0\n" +
                    "WHERE\n" +
                    "user.MOBILE_NUM = " + mobileNum
            );
        }
    }

    /**
     * 数据库-操作-修改手机号密码
     *
     * @param mobileNum
     * @param Password
     */
    public void setMobilePassword(String mobileNum, String password) {
        db.runSQL("" +
                "UPDATE\n" +
                "user\n" +
                "SET\n" +
                "user.`PASSWORD` = " + password + "\n" +
                "WHERE\n" +
                "user.MOBILE_NUM = " + mobileNum
        );
    }

    /**
     * 数据库-操作-修改手机号注册
     *
     * @param mobileNum 手机号
     * @param isUsed    true注册启用
     */
    public void setMobileUse(String mobileNum, boolean isUsed) {
        if (isUsed) {
            db.runSQL("" +
                    "UPDATE\n" +
                    "mobile_warehouse\n" +
                    "SET\n" +
                    "mobile_warehouse.IS_USED = 1\n" +
                    "WHERE\n" +
                    "mobile_warehouse.MOBILE_NUM = " + mobileNum
            );
        } else {
            db.runSQL("" +
                    "UPDATE\n" +
                    "mobile_warehouse\n" +
                    "SET\n" +
                    "mobile_warehouse.IS_USED = 0\n" +
                    "WHERE\n" +
                    "mobile_warehouse.MOBILE_NUM = " + mobileNum
            );
        }

    }
}

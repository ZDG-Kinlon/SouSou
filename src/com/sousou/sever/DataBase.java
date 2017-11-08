package com.sousou.sever;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 自定义数据库类，用于对数据库进行操作，附加SQL语句和语句模板
 *
 * @author kinlon
 * @version 171031-10
 */
public class DataBase {
    private Connection connection;
    private Statement statement;
    private ResultSet rs;
    private String url;
    private String user;
    private String password;
    private String sqlCMD;
    //判断SQL语句是否会修改数据表
    private boolean isSelect;

    //===============================
    //创建get()方法
    //Start
    public Connection getConnection() {
        return connection;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getSqlCMD() {
        return sqlCMD;
    }

    public Statement getStatement() {
        return statement;
    }

    //End
    //创建get()方法
    //===============================

    //===============================
    //创建set()方法
    //Start
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSqlCMD(String sqlCMD) {
        this.isSelect = sqlCMD.substring(0, 6).toUpperCase().equals("SELECT");
        this.sqlCMD = sqlCMD;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    //End
    //创建set()方法
    //===============================

    //===============================
    //初始化设置
    //Start

    /**
     * 构造方法，初始化数据库
     */
    public DataBase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败");
            e.printStackTrace();
        }
    }

    public DataBase(String url, String user, String password) {
        this();
        this.url = url;
        this.user = user;
        this.password = password;
    }
    //End
    //创建内部变量
    //===============================


    /**
     * 连接数据库
     */
    public void contect() {
        try {
            if (this.user == null | this.password == null) {
                System.err.println("用户名或密码未设置");
                this.connection = null;
            } else if (this.user.equals("") | this.password.equals("")) {
                this.connection = DriverManager.getConnection(this.url);
            } else if (!(this.user.equals("") & this.password.equals(""))) {
                this.connection = DriverManager.getConnection(this.url, this.user, this.password);
            }
        } catch (SQLException e) {
            System.out.print("连接数据库失败");
            e.printStackTrace();
            this.connection = null;
        }
    }

    /**
     * 判断数据库是否已经关闭
     *
     * @return 已经关闭返回:true
     */
    public boolean isClose() {
        try {
            return this.connection.isClosed();
        } catch (SQLException e) {
            System.out.println("数据库无法访问");
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 创建数据库对象
     */
    public void statement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("无法创建数据库对象");
            statement = null;
            e.printStackTrace();
        }
    }

    /**
     * 执行SQL查询操作
     */
    public void runSQL() {
        try {
            Log.log("执行SQL语句", this.sqlCMD);
            if (isSelect) {
                rs = statement.executeQuery(this.sqlCMD);
            } else {
                statement.executeUpdate(this.sqlCMD);
            }
        } catch (SQLException e) {
            System.err.println("SQL查询未能执行");
            e.printStackTrace();
        }
    }

    /**
     * SQL查询自定义语句，除了Select的其他语句模板暂无
     * 模板：
     * SELECT    <Distinct>    <这里添加字段>
     * FROM                        <这里添加表>
     * WHERE                      <这里添加条件>：A ? B   ?：=,<>,like,is null,is between,is in list,not like,is not null....
     * <这里添加条件>：多条件时：and,or,and not,or not
     * GROUP BY                  <这里添加字段和排序方式>：asc,desc
     * LIMIT                         <这里添加限制条件>
     *
     * @param strSQL SQL语句
     */
    public void runSQL(String strSQL) {
        this.setSqlCMD(strSQL);
        runSQL();
    }

    /**
     * 返回执行SQL查询的结果，转换为二维数组的抽象表
     *
     * @return 查询到的二维数组
     */
    public String[][] getResult() {
        if (rs == null) {
            System.err.println("SQL查询未执行");
            return null;
        } else {
            try {
                //获取列数
                ResultSetMetaData rsmd = rs.getMetaData();
                int colCount = rsmd.getColumnCount();
                if (colCount == 0) {
                    return null;
                } else {
                    //获取行数
                    rs.last();
                    int rowCount = rs.getRow();
                    if (rowCount == 0) return null;
                    rs.beforeFirst();
                    //写入数组
                    String[][] result = new String[rowCount][colCount];
                    for (int i = 0; rs.next(); i++) {
                        for (int j = 0; j < colCount; j++) {
                            result[i][j] = rs.getString(j + 1);
                        }
                    }
                    return result;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 关闭Result资源
     */
    public void closeResult() {
        try {
            rs.close();
        } catch (SQLException e) {
            System.err.println("Result关闭失败");
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库对象
     */
    public void closeStatement() {
        try {
            statement.close();
        } catch (SQLException e) {
            System.err.println("数据库关闭失败");
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库连接
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("数据库关闭失败");
            e.printStackTrace();
        }
    }

    /**
     * 正常全部关闭
     */
    public void closeDataBase() {
        if (isSelect) closeResult();
        closeStatement();
        closeConnection();
    }
}

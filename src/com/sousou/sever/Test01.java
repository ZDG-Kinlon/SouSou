package com.sousou.sever;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test01 {
    public static void main(String[] args) {
        Connection con;

        String url = "jdbc:mysql://localhost:3306/sousou";
        String user = "kinlon";
        String password = "gdadmin";

        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");

            //2.连接数据库，创建数据库对象
            con = DriverManager.getConnection(url, user, password);

            //3.检测连接状态
            if (!con.isClosed()) {
                System.out.println("数据库登录成功");
            }

            //4.创建SQL命令对象
            Statement statement = con.createStatement();

            //5.SQL语句访问
            String sql = "select * from mobile_warehouse";
            ResultSet rs = statement.executeQuery(sql);


            System.out.println("==============");
            System.out.println("手机号\t已注册");
            System.out.println("--------------");

            String mNum;
            boolean mIsUsed;

            //6.读取字段的值
            while (rs.next()) {
                mIsUsed = rs.getBoolean("IS_USED");
                mNum = rs.getString("MOBILE_NUM");
                System.out.println(mNum + "\t" + mIsUsed);
            }

            //7.关闭资源
            rs.close();
            statement.close();
            con.close();

        } catch (ClassNotFoundException e) {
            System.out.println("没有找到JDBC驱动");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("数据库数据成功获取！！");
        }

    }
}

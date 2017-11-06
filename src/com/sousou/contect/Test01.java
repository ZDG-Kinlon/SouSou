package com.sousou.contect;


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
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(url, user, password);

            if (!con.isClosed()) {
                System.out.println("数据库登录成功");
            }

            Statement statement = con.createStatement();

            String sql = "select * from mobile_warehouse";

            ResultSet rs = statement.executeQuery(sql);

            System.out.println("==============");
            System.out.println("手机号\t已注册");
            System.out.println("--------------");

            String mNum;
            boolean mIsUsed;

            while (rs.next()) {
                mIsUsed = rs.getBoolean("IS_USED");
                mNum = rs.getString("MOBILE_NUM");
                System.out.println(mNum + "\t" + mIsUsed);
            }

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

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//利用JDBC连接MySQL数据库
public class DbUtil {
    private String dbUrl="jdbc:mysql://localhost:3306/jabberwocky?useSSL=false&serverTimezone=UTC&characterEncoding=utf-8";
    private String dbUserName="root";
    private String dbPassword="1234567890";
    private String jdbcName="com.mysql.cj.jdbc.Driver";
    //获取数据库连接
    public Connection getCon (){
        try{
            Class.forName(jdbcName);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection con=null;
        try {
            con=DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    //关闭数据库连接
    public void closeCon(Connection con) throws Exception{
        if(con!=null) {
            con.close();
        }
    }

    public static void main(String[] args) {
        DbUtil dbUtil=new DbUtil();
        try {
            dbUtil.getCon();
            System.out.println("数据库连接成功！");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("数据库连接失败！");
        }
    }
}

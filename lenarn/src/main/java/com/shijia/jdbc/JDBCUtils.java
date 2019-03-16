package com.shijia.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JDBCUtils {
    private static String driver="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://127.0.0.1:3306/news?characterEncoding=utf-8";
    private static String user="root";
    private static String password="root";

    private JDBCUtils(){

    }
    /**
     * 驱动注册
     */
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }

    }

    /**获取连接
     * @return
     * @throws SQLException
     */
    public static Connection  getConnection()throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 关闭连接
     * @param conn
     */
    public static void closeConnection(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //等待垃圾回收
        conn = null;
    }

    /**
     * 释放资源
     * @param conn
     * @param state
     * @param re
     */
    public static void colseResource(Connection conn,Statement state,ResultSet re){
        closeResutl(re);
        closeStatement(state);
        closeConnection(conn);
    }

    /**
     * 释放语句执行者 Statement
     * @param state
     */
    public static void closeStatement(Statement state){
        if(state!=null){
            try {
                state.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        state=null;
    }

    /**
     * 释放结果集 ResultSet
     * @param re
     */
    public static void closeResutl(ResultSet re){
        if(re!=null){
            try {
                re.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        re=null;
    }
}


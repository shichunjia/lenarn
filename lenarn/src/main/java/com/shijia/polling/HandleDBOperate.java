package com.shijia.polling;

import com.shijia.jdbc.JDBCUtils;
import com.shijia.pojo.NewsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HandleDBOperate implements Runnable {
    private static final  String sql="SELECT id,title,newssource,editor,dt,article FROM news_tab WHERE flag='1' LIMIT 20;";
    private static final  String sql2="UPDATE news_tab SET flag='2' WHERE id=?";
    private static final Logger LOGGER = LoggerFactory.getLogger("HandleDBOperate");
    public void run() {
        LOGGER.info("开始读取数据=========");
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        while (true){
            try {
                connection=JDBCUtils.getConnection();
                preparedStatement=connection.prepareStatement(sql) ;
                resultSet=preparedStatement.executeQuery();
                NewsBean newsBean=new NewsBean();
                while (resultSet!=null&&resultSet.next()){
                    newsBean.setId(resultSet.getString("ID"));
                    newsBean.setTitle(resultSet.getString("title"));
                    newsBean.setNewssource(resultSet.getString("newssource"));
                    newsBean.setEditor(resultSet.getString("editor"));
                    newsBean.setArticle(resultSet.getString("article"));
                    preparedStatement=connection.prepareStatement(sql2);
                    preparedStatement.setString(1,newsBean.getId());
                    preparedStatement.execute();
                    try {
                        if(resultSet.getRow()==20){
                            Thread.sleep(10000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("休眠异常");
                    }
                }
             } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                    preparedStatement.close();
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.interrupted();
                }


        }
        }
    }
}

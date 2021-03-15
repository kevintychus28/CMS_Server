package util;

import java.sql.*;

public class DBUtil {

    static String DBurl = "jdbc:mysql://localhost:3306/cms_database";
    static String user = "root";
    static String password = "";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        //1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.创建连接
        Connection connection = DriverManager.getConnection(DBurl,user,password);
        System.out.println("创建连接成功");
        return connection;
    }

    /**用于释放连接 , 执行环境 , 结果集 等资源
     *  @param resultSet 要释放的结果集资源
     *  @param statement 要释放的执行环境资源
     *  @param connection 要释放的连接资源
     * */
    public static void closeAll(ResultSet resultSet, Statement statement, Connection connection) throws SQLException {
        if (resultSet!=null){
            resultSet.close();
        }
        if (statement!=null){
            statement.close();
        }
        if (connection!=null){
            connection.close();
        }

    }
}
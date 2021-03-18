package dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {

    public JSONArray login(String stu_id, String stu_password) {
        Connection connection = null;
        JSONArray jsonarray = new JSONArray();
        JSONObject jsonobj = new JSONObject();
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            //3.写sql语句
            String sql = "select * from student where stu_id=? and stu_password=?";
            //4.获得statement对象
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, stu_id);
            prepareStatement.setString(2, stu_password);
            //5.执行sql 得到结果集
            resultSet = prepareStatement.executeQuery();
            //6.处理结果集
            while (resultSet.next()) {
                jsonobj.put("stu_name", resultSet.getString(3));
                jsonobj.put("stu_sex", resultSet.getString(4));
                jsonobj.put("stu_date", resultSet.getString(5));
                jsonobj.put("stu_class", resultSet.getString(6));
                jsonobj.put("stu_college", resultSet.getString(7));
                jsonarray.add(jsonobj);
            }
            System.out.println(jsonarray);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //7.关闭资源
            try {
                DBUtil.closeAll(resultSet, prepareStatement, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return jsonarray;
    }

}


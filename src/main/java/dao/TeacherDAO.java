package dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDAO {

    public JSONArray login(String tec_id, String tec_password) {
        Connection connection = null;
        JSONArray jsonarray = new JSONArray();
        JSONObject jsonobj = new JSONObject();
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            //3.写sql语句
            String sql = "select * from teacher where tec_id=? and tec_password=?";
            //4.获得statement对象
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, tec_id);
            prepareStatement.setString(2, tec_password);
            //5.执行sql 得到结果集
            resultSet = prepareStatement.executeQuery();
            //6.处理结果集
            while (resultSet.next()) {
                jsonobj.put("tec_name", resultSet.getString(3));
                jsonobj.put("tec_department", resultSet.getString(4));
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


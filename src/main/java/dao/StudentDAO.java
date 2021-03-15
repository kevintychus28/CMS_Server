package dao;

import entity.Student;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> login(String stu_id, String stu_password){
        Connection connection = null;
        List<Student> studentList = new ArrayList<>();
        PreparedStatement prepareStatement  = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            //3.写sql语句
            String sql = "select * from student where id=? and password=?";
            //4.获得statement对象
            prepareStatement  = connection.prepareStatement(sql);
            prepareStatement .setString(1,stu_id);
            prepareStatement .setString(2,stu_password);
            //5.执行sql 得到结果集
            resultSet = prepareStatement .executeQuery();
            //6.处理结果集
            while (resultSet.next()){
                Student student = new Student();
                student.setStu_id(resultSet.getString(1));
                student.setStu_password(resultSet.getString(2));
                studentList.add(student);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //7.关闭资源
            try {
                DBUtil.closeAll(resultSet,prepareStatement,connection);
            } catch (SQLException e){
                e.printStackTrace();
            }

        }

        return studentList;
    }

}


package servlet.admin;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//
@WebServlet(name = "CourseServlet", value = "/CourseServlet")
public class CourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置utf-8，防止出现乱码情况
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Connection connection = null;
        String sql = null;
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;
        JSONArray jsonarray = new JSONArray();
        JSONObject jsonobj = new JSONObject();
        //获取请求参数
        String type = request.getParameter("type");
        try {
            //2.建立数据库链接
            connection = DBUtil.getConnection();
            //3.写sql语句
            if (type.equals("get")) {
                System.out.println("查询课程信息");
                sql = "SELECT cou_id,cou_name,tec_name,cou_classroom,cou_weekday,cou_period,cou_exam_time FROM course a JOIN teacher b ON a.cou_teacher= b.tec_id";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                //5.执行sql 得到结果集
                resultSet = prepareStatement.executeQuery();
                //6.处理结果集，转换成json格式数据
                while (resultSet.next()) {
                    jsonobj.put("cou_id", resultSet.getString(1));
                    jsonobj.put("cou_name", resultSet.getString(2));
                    jsonobj.put("cou_teacher", resultSet.getString(3));
                    jsonobj.put("cou_classroom", resultSet.getString(4));
                    jsonobj.put("cou_weekday", resultSet.getString(5));
                    jsonobj.put("cou_period", resultSet.getString(6));
                    jsonobj.put("cou_exam_time", resultSet.getString(7));
                    jsonarray.add(jsonobj);
                }
                System.out.println(jsonarray);
                //7.返回json数据给客户端
                response.getWriter().write(String.valueOf(jsonarray));
            } else if (type.equals("delete")) {
                System.out.println("删除课程信息");
                String cou_id = request.getParameter("cou_id");
                sql = "DELETE FROM course WHERE cou_id = ?";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, cou_id);
                //5.执行sql 得到结果集 ---> 如果你的SQL语句是诸如update,insert的更新语句，应该用statement的execute()方法
                prepareStatement.execute();
                //6.返回json数据给客户端
                response.getWriter().write("success");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //8.关闭资源
            try {
                DBUtil.closeAll(resultSet, prepareStatement, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置utf-8，防止出现乱码情况
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Connection connection = null;
        String sql = null;
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;
        //获取请求参数
        String type = request.getParameter("type");
        try {
            //2.建立数据库链接
            connection = DBUtil.getConnection();
            //3.写sql语句
            if (type.equals("add")) {
                System.out.println("增加课程信息");
                String cou_name = request.getParameter("cou_name");
                String cou_teacher = request.getParameter("cou_teacher");
                String cou_classroom = request.getParameter("cou_classroom");
                String cou_weekday = request.getParameter("cou_weekday");
                String cou_period = request.getParameter("cou_period");
                String cou_exam_time = request.getParameter("cou_exam_time");
                sql = "INSERT INTO course ( cou_name, cou_teacher,cou_classroom,cou_weekday,cou_period,cou_exam_time) VALUES ( ?,?,?,?,?,?)";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, cou_name);
                prepareStatement.setString(2, cou_teacher);
                prepareStatement.setString(3, cou_classroom);
                prepareStatement.setString(4, cou_weekday);
                prepareStatement.setString(5, cou_period);
                prepareStatement.setString(6, cou_exam_time);
                //5.执行sql 得到结果集 ---> 如果你的SQL语句是诸如update,insert的更新语句，应该用statement的execute()方法
                prepareStatement.execute();
                //6.返回json数据给客户端
                response.getWriter().write("success");
            } else if (type.equals("edit")) {
                System.out.println("编辑学生信息");
                String cou_id = request.getParameter("cou_id");
                String cou_name = request.getParameter("cou_name");
                String cou_teacher = request.getParameter("cou_teacher");
                String cou_classroom = request.getParameter("cou_classroom");
                String cou_weekday = request.getParameter("cou_weekday");
                String cou_period = request.getParameter("cou_period");
                String cou_exam_time = request.getParameter("cou_exam_time");
                sql = "UPDATE course SET cou_name=?,cou_teacher=?,cou_classroom=?,cou_weekday=?,cou_period=?,cou_exam_time=? WHERE cou_id=?";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, cou_name);
                prepareStatement.setString(2, cou_teacher);
                prepareStatement.setString(3, cou_classroom);
                prepareStatement.setString(4, cou_weekday);
                prepareStatement.setString(5, cou_period);
                prepareStatement.setString(6, cou_exam_time);
                prepareStatement.setString(7, cou_id);
                //5.执行sql 得到结果集 ---> 如果你的SQL语句是诸如update,insert的更新语句，应该用statement的execute()方法
                prepareStatement.execute();
                //6.返回json数据给客户端
                response.getWriter().write("success");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //8.关闭资源
            try {
                DBUtil.closeAll(resultSet, prepareStatement, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}

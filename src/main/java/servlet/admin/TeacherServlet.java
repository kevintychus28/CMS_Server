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
@WebServlet(name = "TeacherServlet", value = "/TeacherServlet")
public class TeacherServlet extends HttpServlet {
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
                System.out.println("查询老师信息");
                sql = "SELECT * FROM teacher";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                //5.执行sql 得到结果集
                resultSet = prepareStatement.executeQuery();
                //6.处理结果集，转换成json格式数据
                while (resultSet.next()) {
                    jsonobj.put("tec_id", resultSet.getString(1));
                    jsonobj.put("tec_password", resultSet.getString(2));
                    jsonobj.put("tec_name", resultSet.getString(3));
                    jsonobj.put("tec_department", resultSet.getString(4));
                    jsonarray.add(jsonobj);
                }
                System.out.println(jsonarray);
                //7.返回json数据给客户端
                response.getWriter().write(String.valueOf(jsonarray));
            } else if (type.equals("delete")) {
                System.out.println("删除老师信息");
                String tec_id = request.getParameter("tec_id");
                sql = "DELETE FROM teacher WHERE tec_id = ?";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, tec_id);
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
                System.out.println("增加老师信息");
                String tec_id = request.getParameter("tec_id");
                String tec_password = request.getParameter("tec_password");
                String tec_name = request.getParameter("tec_name");
                String tec_department = request.getParameter("tec_department");
                sql = "INSERT INTO teacher (tec_id,tec_password,tec_name,tec_department ) VALUES (?,?,?,?)";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, tec_id);
                prepareStatement.setString(2, tec_password);
                prepareStatement.setString(3, tec_name);
                prepareStatement.setString(4, tec_department);
                //5.执行sql 得到结果集 ---> 如果你的SQL语句是诸如update,insert的更新语句，应该用statement的execute()方法
                prepareStatement.execute();
                //6.返回json数据给客户端
                response.getWriter().write("success");
            } else if (type.equals("edit")) {
                System.out.println("编辑老师信息");
                String tec_id = request.getParameter("tec_id");
                String tec_password = request.getParameter("tec_password");
                String tec_name = request.getParameter("tec_name");
                String tec_department = request.getParameter("tec_department");
                sql = "UPDATE teacher SET tec_password=?,tec_name=?,tec_department=? WHERE tec_id=?";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, tec_password);
                prepareStatement.setString(2, tec_name);
                prepareStatement.setString(3, tec_department);
                prepareStatement.setString(4, tec_id);
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

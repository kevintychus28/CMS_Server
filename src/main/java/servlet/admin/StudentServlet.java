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
@WebServlet(name = "StudentServlet", value = "/StudentServlet")
public class StudentServlet extends HttpServlet {
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
                sql = "SELECT * FROM student";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                //5.执行sql 得到结果集
                resultSet = prepareStatement.executeQuery();
                //6.处理结果集，转换成json格式数据
                while (resultSet.next()) {
                    jsonobj.put("stu_id", resultSet.getString(1));
                    jsonobj.put("stu_password", resultSet.getString(2));
                    jsonobj.put("stu_name", resultSet.getString(3));
                    jsonobj.put("stu_sex", resultSet.getString(4));
                    jsonobj.put("stu_date", resultSet.getString(5));
                    jsonobj.put("stu_class", resultSet.getString(6));
                    jsonobj.put("stu_college", resultSet.getString(7));
                    jsonarray.add(jsonobj);
                }
                System.out.println(jsonarray);
                //7.返回json数据给客户端
                response.getWriter().write(String.valueOf(jsonarray));
            } else if (type.equals("delete")) {
                String stu_id = request.getParameter("stu_id");
                sql = "DELETE FROM table_name WHERE stu_id = ?";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, stu_id);
                //5.执行sql 得到结果集
                resultSet = prepareStatement.executeQuery();
                System.out.println(jsonarray);
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
                String stu_id = request.getParameter("stu_id");
                String stu_password = request.getParameter("stu_password");
                String stu_name = request.getParameter("stu_name");
                String stu_sex = request.getParameter("stu_sex");
                String stu_date = request.getParameter("stu_date");
                String stu_class = request.getParameter("stu_class");
                String stu_college = request.getParameter("stu_college");
                sql = "INSERT INTO student ( stu_id, stu_password,stu_name,stu_sex,stu_date,stu_class,stu_college ) VALUES ( ?,?,?,?,?,?,?)";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, stu_id);
                prepareStatement.setString(2, stu_password);
                prepareStatement.setString(3, stu_name);
                prepareStatement.setString(4, stu_sex);
                prepareStatement.setString(5, stu_date);
                prepareStatement.setString(6, stu_class);
                prepareStatement.setString(7, stu_college);
                //5.执行sql 得到结果集
                resultSet = prepareStatement.executeQuery();
                //6.返回json数据给客户端
                response.getWriter().write("success");
            } else if (type.equals("edit")) {
                String stu_id = request.getParameter("stu_id");
                String stu_password = request.getParameter("stu_password");
                String stu_name = request.getParameter("stu_name");
                String stu_sex = request.getParameter("stu_sex");
                String stu_date = request.getParameter("stu_date");
                String stu_class = request.getParameter("stu_class");
                String stu_college = request.getParameter("stu_college");
                sql = "UPDATE student SET stu_password=?,stu_name=?,stu_sex=?,stu_date=?,stu_class=?,stu_college=? WHERE stu_id=?";
                //4.获得statement对象
                prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, stu_password);
                prepareStatement.setString(2, stu_name);
                prepareStatement.setString(3, stu_sex);
                prepareStatement.setString(4, stu_date);
                prepareStatement.setString(5, stu_class);
                prepareStatement.setString(6, stu_college);
                prepareStatement.setString(7, stu_id);
                //5.执行sql 得到结果集
                resultSet = prepareStatement.executeQuery();
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

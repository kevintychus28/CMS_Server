package servlet;

import util.DBUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "AddNoteServlet", value = "/AddNoteServlet")
public class AddNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置utf-8，防止出现乱码情况
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;

        //1.获取账号，笔记标题，笔记内容
        String userID = request.getParameter("userID");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        System.out.println(userID + "；" + "新增笔记信息：" + title + "；" + content);
        try {
            //2.建立数据库链接
            connection = DBUtil.getConnection();
            //3.写sql语句
            String sql = "INSERT INTO note (userID, title, content) VALUES ( ?, ?, ? )";
            //4.获得statement对象
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, userID);
            prepareStatement.setString(2, title);
            prepareStatement.setString(3, content);
            //5.执行sql 得到结果集
            prepareStatement.execute();
            //6.返回结果给客户端
            response.getWriter().write("success");
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

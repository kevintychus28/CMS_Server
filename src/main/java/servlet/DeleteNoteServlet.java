package servlet;

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

@WebServlet(name = "DeleteNoteServlet", value = "/DeleteNoteServlet")
public class DeleteNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置utf-8，防止出现乱码情况
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;

        //1.获取账号，笔记编号
        String userID = request.getParameter("userID");
        String note_id = request.getParameter("note_id");
        try {
            //2.建立数据库链接
            connection = DBUtil.getConnection();
            //3.写sql语句
            String sql = "DELETE FROM note WHERE userID = ? AND note_id= ?";
            //4.获得statement对象
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, userID);
            prepareStatement.setString(2, note_id);
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


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        doGet(request,response);
    }
}

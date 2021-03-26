package servlet.score;

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

@WebServlet(name = "EditScoreServlet", value = "/EditScoreServlet")
public class EditScoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("收到post请求");
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置utf-8，防止出现乱码情况
        System.out.println("收到get请求");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;

        //1.获取账号，课程名称，成绩
        String userID = request.getParameter("userID");
        String cou_name = request.getParameter("cou_name");
        String stu_name = request.getParameter("stu_name");
        String grade = request.getParameter("grade");

        System.out.println(userID + "；" + "修改成绩信息：" + cou_name + " - " + grade);

        try {
            //2.建立数据库链接
            connection = DBUtil.getConnection();
            //3.写sql语句
            String sql = "UPDATE SCHEDULE a JOIN course b ON a.cou_id = b.cou_id JOIN student c ON a.stu_id = c.stu_id SET a.grade = ? WHERE c.stu_name = ? AND b.cou_name = ? AND b.cou_teacher = ?";
            //4.获得statement对象
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, grade);
            prepareStatement.setString(2, stu_name);
            prepareStatement.setString(3, cou_name);
            prepareStatement.setString(4, userID);

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

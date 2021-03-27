package servlet.exam;

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

@WebServlet(name = "ExamServlet", value = "/ExamServlet")
public class ExamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置utf-8，防止出现乱码情况
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;
        JSONArray jsonarray = new JSONArray();
        JSONObject jsonobj = new JSONObject();

        //1.获取账号
        String userID = request.getParameter("userID");
        String identity = request.getParameter("identity");
        System.out.println(userID + identity);
        try {
            //2.建立数据库链接
            connection = DBUtil.getConnection();
            //3.写sql语句
            String sql;
            if (identity.equals("student")) {
                sql = "SELECT cou_name, cou_exam_time FROM course a JOIN schedule b ON a.cou_id = b.cou_id AND b.stu_id = ? AND TRIM(cou_exam_time)!=''";
            } else {
                sql = "SELECT cou_name, cou_exam_time FROM course WHERE cou_teacher = ? AND TRIM(cou_exam_time)!=''";
            }
            //4.获得statement对象
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, userID);
            //5.执行sql 得到结果集
            resultSet = prepareStatement.executeQuery();
            //6.处理结果集，转换成json格式数据
            while (resultSet.next()) {
                jsonobj.put("cou_name", resultSet.getString(1));
                jsonobj.put("cou_exam_time", resultSet.getString(2));
                jsonarray.add(jsonobj);
            }
            System.out.println(jsonarray);
            //7.返回json数据给客户端
            response.getWriter().write(String.valueOf(jsonarray));
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
        doGet(request, response);
    }

}

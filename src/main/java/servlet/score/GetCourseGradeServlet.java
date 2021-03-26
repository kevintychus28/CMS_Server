package servlet.score;

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
@WebServlet(name = "GetCourseGradeServlet", value = "/GetCourseGradeServlet")
public class GetCourseGradeServlet extends HttpServlet {
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

        //1.获取教师账号和课程名称
        String tec_id = request.getParameter("userID");
        String cou_name = request.getParameter("cou_name");
        System.out.println(tec_id + cou_name);
        try {
            //2.建立数据库链接
            connection = DBUtil.getConnection();
            //3.写sql语句
            String sql = "SELECT stu_name ,grade FROM course a JOIN schedule b ON a.cou_id = b.cou_id JOIN student c ON c.stu_id = b.stu_id AND a.cou_teacher = ? AND a.cou_name = ?";
            //4.获得statement对象
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, tec_id);
            prepareStatement.setString(2, cou_name);
            //5.执行sql 得到结果集
            resultSet = prepareStatement.executeQuery();
            //6.处理结果集，转换成json格式数据
            while (resultSet.next()) {
                jsonobj.put("name", resultSet.getString(1));
                jsonobj.put("grade", resultSet.getString(2));
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

    }
}

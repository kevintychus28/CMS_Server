package servlet.schedule;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DBUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ScheduleServlet", value = "/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {

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
        String stu_id = request.getParameter("userID");
        String identity = request.getParameter("identity");
        System.out.println(stu_id + identity);
        try {
            //2.建立数据库链接
            connection = DBUtil.getConnection();
            //3.写sql语句
            String sql;
            if (identity.equals("student")) {
                sql = "SELECT cou_name, tec_name, cou_classroom, cou_weekday, cou_period, start_time FROM course a JOIN schedule b ON a.cou_id = b.cou_id AND b.stu_id = ? JOIN teacher c ON a.cou_teacher = c.tec_id JOIN time d ON a.cou_period = d.period ORDER BY cou_period";
            } else {
                sql = "SELECT cou_name, tec_name, cou_classroom, cou_weekday, cou_period, start_time FROM course a	JOIN teacher b ON a.cou_teacher = b.tec_id AND a.cou_teacher = ? JOIN time c ON a.cou_period = c.period ORDER BY cou_period";
            }
            //4.获得statement对象
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, stu_id);
            //5.执行sql 得到结果集
            resultSet = prepareStatement.executeQuery();
            //6.处理结果集，转换成json格式数据
            while (resultSet.next()) {
                jsonobj.put("cou_name", resultSet.getString(1));
                jsonobj.put("cou_teacher", resultSet.getString(2));
                jsonobj.put("cou_classroom", resultSet.getString(3));
                jsonobj.put("cou_weekday", resultSet.getString(4));
                jsonobj.put("cou_period", resultSet.getString(5));
                jsonobj.put("start_time", resultSet.getString(6));
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

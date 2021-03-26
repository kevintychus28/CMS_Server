package servlet.login;

import dao.StudentDAO;
import dao.TeacherDAO;
import entity.Student;
import entity.Teacher;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoginServletTec", value = "/LoginServletTec")
public class LoginServletTec extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置utf-8，防止出现乱码情况
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        //获取登录的用户名
        String tec_id = request.getParameter("userID");
        //获取登录的密码
        String tec_password = request.getParameter("password");

        System.out.println("账号:" + tec_id);
        System.out.println("密码:" + tec_password);

        //获取数据库中符合目标的数据
        TeacherDAO teacherDAO = new TeacherDAO();
        JSONArray teaJson = teacherDAO.login(tec_id, tec_password);
        List<Teacher> teacherList = teaJson;
        System.out.println("teacherList:" + teacherList);

        if (teacherList.size() != 0) {
            HttpSession session = request.getSession();
            session.setAttribute("userID", tec_id);
            response.getWriter().write(String.valueOf(teaJson));
            System.out.println("stuJson" + String.valueOf(teaJson));
        }

    }

    public void destroy() {
    }
}
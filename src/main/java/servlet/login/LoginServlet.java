package servlet.login;

import dao.StudentDAO;
import entity.Student;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {


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

        //获取注册的用户名
        String stu_id = request.getParameter("userID");
        //获取注册的密码
        String stu_password = request.getParameter("password");

        System.out.println("账号:" + stu_id);
        System.out.println("密码:" + stu_password);

        //获取数据库中符合目标的数据
        StudentDAO studentDao = new StudentDAO();
        JSONArray stuJson = studentDao.login(stu_id, stu_password);
        List<Student> studentList = stuJson;
        System.out.println("studentList:" + studentList);

        if (studentList.size() != 0) {
            HttpSession session = request.getSession();
            session.setAttribute("userID", stu_id);
            response.getWriter().write(String.valueOf(stuJson));
            System.out.println("stuJson" + String.valueOf(stuJson));
        }

    }

    public void destroy() {
    }
}
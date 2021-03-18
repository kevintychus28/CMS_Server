package util;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * 过滤器的使用方法
 * 在创建类的时候 要用到接口Filter
 * 要自己在web.xml文件中配置过滤器
 */
public class MyCharacterEncodingFilter extends HttpServlet implements Filter {

    private String encoding=null;

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("encoding"); //获得配置文件中的encoding
    }
}
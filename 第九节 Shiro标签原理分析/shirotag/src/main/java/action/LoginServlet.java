package action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jay.zhou
 * @date 2018/12/26
 * @time 7:41
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/authen")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //获取输入的帐号密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //封装用户数据，成为Shiro能认识的token标识
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //将封装用户信息的token进行验证
        boolean isLoginSuccess = login(token);
        if (!isLoginSuccess) {
            //重定向到未登录成功页面
            resp.sendRedirect("loginFailure.jsp");
            return;
        }
        req.getRequestDispatcher("loginSuccess.jsp").forward(req, resp);
    }

    /**
     * 用于验证用户的帐号密码信息是否合法
     *
     * @param token 封装着用户的帐号密码的UsernamePasswordToken
     * @return 用户输入的信息是否合法
     */
    private boolean login(UsernamePasswordToken token) {
        //从shiro环境中获取门面Subject
        Subject subject = SecurityUtils.getSubject();
        try {
            //认证，内部读取shiro.ini文件进行帐号匹配
            subject.login(token);
        } catch (Exception e) {
            return false;
        }
        return subject.isAuthenticated();
    }
}


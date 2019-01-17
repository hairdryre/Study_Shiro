package com.jay.controller;

import com.jay.domain.UserEntity;
import com.jay.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;
import static org.apache.shiro.SecurityUtils.getSubject;
import static org.apache.shiro.web.servlet.Cookie.ONE_YEAR;


/**
 * @author jay.zhou
 * @date 2019/1/10
 * @time 13:27
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/obtainAllUsers")
    @ResponseBody
    public List<UserEntity> getAllUser() {
        return userService.findAllUser();
    }

    @RequestMapping(value = "security/login", method = {RequestMethod.POST})
    public ModelAndView authentication(@RequestParam("username") String userName, @RequestParam("password") String password,
                                       @RequestParam(value = "isRememberMe", required = false) Boolean isRememberMe,
                                       HttpServletRequest request, HttpServletResponse response) {
        if (isRememberMe == null) {
            isRememberMe = false;
        }
        request.getSession().setAttribute("abc", "def");
        //获取到Subject门面对象
        Subject subject = getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
        //先设置是否记住我
        usernamePasswordToken.setRememberMe(isRememberMe);
        final Cookie rememberCookie = new SimpleCookie("projectKey");
        try {
            subject.login(usernamePasswordToken);
            if (isRememberMe && userName.length() > 0) {
                //cookie 保存一年
                rememberCookie.setMaxAge(ONE_YEAR);
                rememberCookie.setHttpOnly(TRUE);
                //用于同一服务器内的cookie共享路径
                rememberCookie.setPath("/");
                //存储用户名
                rememberCookie.setValue(userName);
                //将此cookie存储到当前请求中
                rememberCookie.saveTo(request, response);
            } else {
                rememberCookie.setPath("/");
                //移除此cookie
                rememberCookie.removeFrom(request, response);
            }
        } catch (ExcessiveAttemptsException exception) {
            if (!subject.isAuthenticated()) {
                Map<String, String> failReason = new HashMap<String, String>(1);
                failReason.put("msg", "登录次数已经超过限制，请一分钟后重试");
                //登录失败
                return new ModelAndView("fail", failReason);
            }
        } catch (AuthenticationException exception) {
            if (!subject.isAuthenticated()) {
                Map<String, String> failReason = new HashMap<String, String>(1);
                failReason.put("msg", "帐号密码错误");
                //登录失败
                return new ModelAndView("fail", failReason);
            }
        }
        //登录成功
        return new ModelAndView("home");

    }

    @RequestMapping(value = "admin")
    public String enterAdmin() {
        //跳转到 web-inf/pages/admin.jsp页面
        System.out.println(getSubject().getSession().getAttribute("abc"));
        return "admin";
    }

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request) {
        System.out.println(getSubject().getSession().getAttribute("abc"));
        //获取客户本机cookie数据
        final Cookie rememberCookie = new SimpleCookie("projectKey");
        rememberCookie.setPath("/");
        //从请求中获取加密cookie数据
        final String usernameFromCookie = rememberCookie.readValue(request, null);
        request.getSession().setAttribute("username", usernameFromCookie);
        return "login";
    }

}

package com.jay.controller;

import com.jay.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.registry.infomodel.User;
import java.util.List;

import static org.apache.shiro.SecurityUtils.getSubject;


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
    public List<User> getAllUser() {
        return userService.findAllUser();
    }

    @RequestMapping(value = "security/login", method = {RequestMethod.POST})
    public String login(@RequestParam("username") String userName, @RequestParam("password") String password) {
        //获取到Subject门面对象
        Subject subject = getSubject();
        try {
            //将用户数据交给Shiro框架去做
            //你可以在自定义Realm中的认证方法doGetAuthenticationInfo()处打个断点
            subject.login(new UsernamePasswordToken(userName, password));
        } catch (AuthenticationException exception) {
            if (!subject.isAuthenticated()) {
                //登录失败
                return "fail";
            }
        }
        //登录成功
        return "home";

    }

    @RequestMapping(value = "admin")
    public String enterAdmin() {
        //跳转到 web-inf/pages/admin.jsp页面
        return "admin";
    }

}

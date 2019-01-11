package com.jay.controller;

import com.jay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.registry.infomodel.User;
import java.util.List;

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
}

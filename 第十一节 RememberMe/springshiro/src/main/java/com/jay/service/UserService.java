package com.jay.service;

import javax.xml.registry.infomodel.User;
import java.util.List;

/**
 * @author jay.zhou
 * @date 2019/1/10
 * @time 13:25
 */
public interface UserService {

    List<User> findAllUser();

    String findPasswordByName(String username);

}

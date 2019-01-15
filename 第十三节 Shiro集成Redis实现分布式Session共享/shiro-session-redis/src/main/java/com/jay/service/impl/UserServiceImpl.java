package com.jay.service.impl;

import com.jay.dao.UserDao;
import com.jay.domain.UserEntity;
import com.jay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jay.zhou
 * @date 2019/1/10
 * @time 13:26
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserEntity> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public String findPasswordByName(String username) {
        return userDao.findPasswordByName(username);
    }
}

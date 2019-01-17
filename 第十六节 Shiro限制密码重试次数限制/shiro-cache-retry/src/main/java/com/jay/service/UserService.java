package com.jay.service;

import com.jay.domain.UserEntity;

import java.util.List;

/**
 * @author jay.zhou
 * @date 2019/1/10
 * @time 13:25
 */
public interface UserService {

    List<UserEntity> findAllUser();

    String findPasswordByName(String username);

}

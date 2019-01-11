package com.jay.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.xml.registry.infomodel.User;
import java.util.List;

/**
 * @author jay.zhou
 * @date 2019/1/10
 * @time 13:20
 */
@Repository
public interface UserDao {

    List<User> findAllUser();

    String findPasswordByName(@Param("username")String username);
}

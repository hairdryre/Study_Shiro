package com.jay.dao;

import com.jay.domain.UserEntity;
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

    List<UserEntity> findAllUser();

    String findPasswordByName(@Param("username")String username);
}

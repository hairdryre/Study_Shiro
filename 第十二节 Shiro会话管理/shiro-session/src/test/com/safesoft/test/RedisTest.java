package com.safesoft.test;

import com.jay.redis.RedisService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author jay.zhou
 * @date 2019/1/15
 * @time 11:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
	private RedisService redisService;


    @Test
    public void testString() {
        redisService.setEx("username","jay",1000L);
        Assert.assertEquals("jay", redisService.get("username"));
    }

}

package com.safesoft;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author jay.zhou
 * @date 2019/1/13
 * @time 12:25
 */
public class MyFirstTest {
    @Test
    public void test() {
        //读取配置文件，相当于在加载数据源
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro_1.ini");
        //SecurityManager 是Shiro内部的底层实现，几乎所有功能都由其实现
        org.apache.shiro.mgt.SecurityManager sm = factory.getInstance();
        //SecurityUtils是一个工具，方便用户调用，它封装了SecurityManager
        SecurityUtils.setSecurityManager(sm);
        //生成一个SecurityManager的门面类，即Subject。
        Subject subject = SecurityUtils.getSubject();
        //封装用户的数据
        UsernamePasswordToken token = new UsernamePasswordToken("jay", "123");
        //Subject接收到的方法参数，最终将会传到SecurityManager中进行验证
        //将用户的数据token 最终传递到Realm中进行对比
        subject.login(token);
        //判断本帐号是否已经被认证
        Assert.assertEquals(true, subject.isAuthenticated());
    }
}

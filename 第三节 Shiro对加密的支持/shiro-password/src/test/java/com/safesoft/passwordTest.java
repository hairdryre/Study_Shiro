package com.safesoft;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author jay.zhou
 * @date 2019/1/13
 * @time 12:50
 */
public class passwordTest {
    @Test
    public void testPassword(){
        String password = "123456";
        Md5Hash md5Hash = new Md5Hash(password);
        //打印结果：e10adc3949ba59abbe56e057f20f883e
        System.out.println(md5Hash);
    }

    @Test
    public void testPassword2(){
        String password = "123456";
        String csdnName = "小大宇";
        //第二个参数就是 盐salt
        Md5Hash md5Hash = new Md5Hash(password,csdnName);
        //打印结果：6e588f84bc14b24845f9d2859665e4e5
        System.out.println(md5Hash);
    }

    @Test
    public void testPassword3(){
        String password = "123456";
        String csdnName = "小大宇";
        Md5Hash md5Hash = new Md5Hash(password,csdnName, 3);
        //打印结果：e36e406088fcbe05c70ec1ab33bffa64
        System.out.println(md5Hash);
    }

    @Test
    public void testPasswordRealm() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        //验证密码123456是否能够登录成功
        UsernamePasswordToken token = new UsernamePasswordToken("anyString", "123456");
        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            e.printStackTrace();
        }
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        //6、退出
        subject.logout();
    }
}

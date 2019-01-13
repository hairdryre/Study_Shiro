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
 * @time 13:06
 */
public class PermissionTest {
    @Test
    public void testPermissionRealm() {
        Subject subject = login("anyUsername", "anyPassword");
        //使用断言判断用户是否已经登录
        Assert.assertTrue(subject.isAuthenticated());
        //---------登录结束------------

        //---------检查当前用户的角色信息------------
        System.out.println(subject.hasRole("coder"));
        //---------如果当前用户有此角色，无返回值。若没有此权限，则抛 UnauthorizedException------------
        subject.checkRole("coder");
        //---------检查当前用户的权限信息------------
        System.out.println(subject.isPermitted("code:insert"));
        //---------如果当前用户有此权限，无返回值。若没有此权限，则抛 UnauthorizedException------------
        subject.checkPermissions("code:insert", "code:update");
}

    private Subject login(String yourInputUsername, String yourInputPassword) {
        //读取配置文件
        Factory<org.apache.shiro.mgt.SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =
                new UsernamePasswordToken(yourInputUsername, yourInputPassword);
        //调用自定义Realm的doGetAuthenticationInfo方法进行认证操作
        subject.login(token);
        //返回当前shiro环境的门面
        return subject;
    }

}

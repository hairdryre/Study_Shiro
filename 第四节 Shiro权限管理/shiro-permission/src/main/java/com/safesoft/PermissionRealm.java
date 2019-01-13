package com.safesoft;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Arrays;
import java.util.List;

/**
 * @author jay.zhou
 * @date 2019/1/13
 * @time 13:03
 */
public class PermissionRealm extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String yourInputUsername = (String) principals.getPrimaryPrincipal();
        //构造一个授权凭证
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //通过你的用户名查询数据库，得到你的权限信息与角色信息。并存放到权限凭证中
        info.addRole(getYourRoleByUsernameFromDB(yourInputUsername));
        info.addStringPermissions(getYourPermissionByUsernameFromDB(yourInputUsername));
        //返回你的权限信息
        return info;
    }

    private String getYourRoleByUsernameFromDB(String username) {
        return "coder";
    }

    private List<String> getYourPermissionByUsernameFromDB(String username) {
        return Arrays.asList("code:insert", "code:update");
    }


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String yourInputUsername = (String) token.getPrincipal();
        String yourInputPassword = new String((char[]) token.getCredentials());
        //默认要被验证的密码就是用户输入的密码，所以用户输入什么密码都是对的
        String passwordFromDB = yourInputPassword;
        return new SimpleAuthenticationInfo(yourInputUsername, passwordFromDB, getName());
    }
}

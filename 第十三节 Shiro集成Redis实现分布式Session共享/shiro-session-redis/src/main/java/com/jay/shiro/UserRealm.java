package com.jay.shiro;

/**
 * @author jay.zhou
 * @date 2019/1/10
 * @time 14:23
 */

import com.jay.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author jay.zhou
 * @date 2019/1/10
 * @time 10:57
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 强制重写的认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //还记得吗，token封装了客户端的帐号密码，由Subject拉客并最终带到此处
        String clientUsername = (String) token.getPrincipal();
        //从数据库中查询帐号密码
        String passwordFromDB = userService.findPasswordByName(clientUsername);
        if (passwordFromDB == null) {
            //如果根据用户输入的用户名，去数据库中没有查询到相关的密码
            throw new UnknownAccountException();
        }

        /**
         * 返回一个从数据库中查出来的的凭证。用户名为clientUsername，密码为passwordFromDB 。封装成当前返回值
         * 接下来shiro框架做的事情就很简单了。
         * 它会拿你的输入的token与当前返回的这个数据库凭证SimpleAuthenticationInfo对比一下
         * 看看是不是一样，如果用户的帐号密码与数据库中查出来的数据一样，那么本次登录成功
         * 否则就是你密码输入错误
         */
        return new SimpleAuthenticationInfo(clientUsername, passwordFromDB, "UserRealm");
    }

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
        return Arrays.asList("code：insert", "code：update");
    }

}

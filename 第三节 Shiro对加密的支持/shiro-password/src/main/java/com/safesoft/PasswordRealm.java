package com.safesoft;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @author jay.zhou
 * @date 2018/12/20
 * @time 23:07
 */
public class PasswordRealm extends AuthorizingRealm {

    public PasswordRealm() {
        //采用md5算法
        HashedCredentialsMatcher passwordMatcher = new HashedCredentialsMatcher("md5");
        //循环加密3次
        passwordMatcher.setHashIterations(3);
        //再将这个加密组件注入到我们的Realm中
        this.setCredentialsMatcher(passwordMatcher);
    }

    @Override
    public String getName() {
        return "PasswordRealm";
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String clientUsername = (String) token.getPrincipal();
        String yourExpectedSalt = "小大宇";
        return new SimpleAuthenticationInfo(clientUsername, getPasswordFromDB(clientUsername),
                generateSalt(yourExpectedSalt), getName());
    }

    private String getPasswordFromDB(String clientUsername) {
        //模拟从数据库查出来的密文
        return "e36e406088fcbe05c70ec1ab33bffa64";
    }

    private ByteSource generateSalt(String salt) {
        return ByteSource.Util.bytes(salt);
    }

    //授权，暂时不谈
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}

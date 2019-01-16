package com.jay.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jay.zhou
 * @date 2019/1/10
 * @time 14:34
 */
@Component("userAccessControlFilter")
public final class UserAccessControlFilter extends AccessControlFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccessControlFilter.class);

    /**
     * 即是否允许访问，返回true表示允许.
     * 如果返回false，则进入本类的onAccessDenied方法中进行处理
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object object)
            throws Exception {
        final Subject subject = SecurityUtils.getSubject();

        //判断用户是否进行过登录认证，如果没经过认证则返回登录页
        if (subject.getPrincipal() == null || !subject.isAuthenticated()) {
            return Boolean.FALSE;
        }

        final String requestURI = this.getPathWithinApplication(request);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("请求URL为:{}", requestURI);
        }

        //此处可以编写用于判断用户是否有相关权限的代码
        //subject.hasRole("需要的角色");
        //subject.isPermitted("需要的权限");
        return Boolean.TRUE;
    }

    /**
     * 如果返回true，则继续执行其它拦截器
     * 如果返回false，则表示拦截住本次请求，且在代码中规定处理方法为重定向到登录页面
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse)
            throws Exception {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("当前帐号没有相应的权限!");
        }

        //重定向到登录页面
        this.redirectToLogin(servletRequest, servletResponse);
        return Boolean.FALSE;
    }
}

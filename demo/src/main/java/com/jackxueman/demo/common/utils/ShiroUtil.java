package com.jackxueman.demo.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * shiro工具类
 */
@Component
public final class ShiroUtil {

    private ShiroUtil() {

    }

    /**
     * 获取当前用户身份证
     *
     * @return
     */
    public static String getCurrentUserIdentity() {
        Subject subject = SecurityUtils.getSubject();
        return (String) subject.getPrincipal();
    }

    /**
     * 获取当前用户id
     *
     * @return
     */
    public static Long getCurrentUserId() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        return (Long) session.getAttribute("user_id");
    }

    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getCurrentUserName() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        return (String) session.getAttribute("user_name");
    }
}

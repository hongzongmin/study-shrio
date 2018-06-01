package com.jackxueman.demo.common.interceptor;

import com.jackxueman.demo.auth.RedisSessionDao;
import com.jackxueman.demo.common.enums.ResultCodeEnum;
import com.jackxueman.demo.common.exception.BaseException;
import com.jackxueman.demo.common.utils.ShiroUtil;
import com.jackxueman.demo.pojo.entity.User;
import com.jackxueman.demo.service.AuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * session拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisSessionDao redisSessionDao;

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String identity = (String) subject.getPrincipal();

        // session 失效
        if (identity == null || session == null || session.getId() == null) {
            throw new BaseException(ResultCodeEnum.SESSION_TIMEOUT);
        }

        String sessionId = session.getId().toString();

        // 获取redis中存储的session id，不同说明被踢出
        String oldSessionId = redisSessionDao.read(identity);
        if (!sessionId.equals(oldSessionId)) {
            throw new BaseException(ResultCodeEnum.SESSION_INVALID);
        }
        redisSessionDao.update(session, identity);

        // ip和mac鉴权
        Long userId = ShiroUtil.getCurrentUserId();
        User user = userId == null ? authService.getUser(identity) : authService.getUser(userId);

        if (user == null) {
            throw new BaseException(ResultCodeEnum.NAME_PASSWORD_ERROR);
        }
        if (!authService.checkIpValid(user)) {
            throw new BaseException(ResultCodeEnum.IP_LIMIT);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {
    }
}

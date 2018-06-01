package com.jackxueman.demo.auth;

import com.jackxueman.demo.common.enums.ResultCodeEnum;
import com.jackxueman.demo.common.enums.UserStatusEnum;
import com.jackxueman.demo.common.exception.BaseException;
import com.jackxueman.demo.common.utils.ShiroUtil;
import com.jackxueman.demo.pojo.entity.User;
import com.jackxueman.demo.service.AuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Objects;

/**
 * 授权策略
 */
public class AuthRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private AuthService authService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Long id = ShiroUtil.getCurrentUserId();
        User user = authService.getUser(id);

        if (user == null) {
            throw new BaseException(ResultCodeEnum.NAME_PASSWORD_ERROR);
        }
        if (!ShiroUtil.getCurrentUserIdentity().equals(user.getIdentity())) {
            throw new BaseException(ResultCodeEnum.NAME_PASSWORD_ERROR);
        }
        if (UserStatusEnum.NORMAL.getCode() != user.getStatus()) {
            throw new BaseException(ResultCodeEnum.USER_INVALID);
        }
        if (user.getDepartmentId() == null) {
            throw new BaseException(ResultCodeEnum.DEPARTMENT_NOT_ASSIGNED);
        }
        if (authService.countRoles(id) == 0) {
            throw new BaseException(ResultCodeEnum.ROLE_NOT_ASSIGNED);
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(authService.listPrivs(id));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws
            AuthenticationException {
        String identity = (String) authenticationToken.getPrincipal();

        User user = authService.getUser(identity);
        if (user == null) {
            throw new BaseException(ResultCodeEnum.NAME_PASSWORD_ERROR);
        }
        if (Objects.equals(UserStatusEnum.FORBIDDEN.getCode(), user.getStatus())) {
            throw new BaseException(ResultCodeEnum.USER_INVALID);
        }
        if (user.getDepartmentId() == null) {
            throw new BaseException(ResultCodeEnum.DEPARTMENT_NOT_ASSIGNED);
        }
        if (authService.countRoles(user.getId()) == 0) {
            throw new BaseException(ResultCodeEnum.ROLE_NOT_ASSIGNED);
        }
        if (!authService.checkIpValid(user)) {
            throw new BaseException(ResultCodeEnum.IP_LIMIT);
        }

        SecurityUtils.getSubject().getSession().setAttribute("user_id", user.getId());
        SecurityUtils.getSubject().getSession().setAttribute("user_name", user.getName());
        SecurityUtils.getSubject().getSession().setAttribute("department_id", user.getDepartmentId());
        return new SimpleAuthenticationInfo(user.getIdentity(), user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), this.getName());
    }
}

package com.jackxueman.demo.service.impl;


import com.jackxueman.demo.auth.RedisSessionDao;
import com.jackxueman.demo.common.enums.ResultCodeEnum;
import com.jackxueman.demo.common.enums.YesNoEnum;
import com.jackxueman.demo.common.exception.BaseException;
import com.jackxueman.demo.common.utils.ApplicationContextUtil;
import com.jackxueman.demo.common.utils.ShiroUtil;
import com.jackxueman.demo.mapper.RolePrivilegeMapper;
import com.jackxueman.demo.mapper.UserRoleMapper;
import com.jackxueman.demo.mapper.generate.UserSrcMapper;
import com.jackxueman.demo.pojo.entity.*;
import com.jackxueman.demo.pojo.request.AuthLoginReqVO;
import com.jackxueman.demo.pojo.request.AuthRestPwdReqVO;
import com.jackxueman.demo.pojo.response.AuthLoginInfoRespVO;
import com.jackxueman.demo.pojo.response.AuthResetPwdRespVO;
import com.jackxueman.demo.service.AuthService;
import com.jackxueman.demo.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 授权service实现类
 */
@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private RedisSessionDao redisSessionDao;

    @Autowired
    private UserSrcMapper userSrcMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePrivilegeMapper rolePrivilegeMapper;


    @Autowired
    private UserService userService;

    /**
     * 获取用户登录信息
     */
    @Override
    public AuthLoginInfoRespVO getLoginInfo() throws UnsupportedEncodingException {
        // 获取用户信息
        User user = getService().getUser(ShiroUtil.getCurrentUserId());
        if (user == null) {
            logout();
            throw new BaseException(ResultCodeEnum.AUTH_FAIL);
        }

        AuthLoginInfoRespVO respVO = new AuthLoginInfoRespVO();
        respVO.setId(user.getId());
        respVO.setName(user.getName());
        respVO.setIdentity(user.getIdentity());
        respVO.setAdmin("admin".equals(user.getName()));
        respVO.setDepartmentId(user.getDepartmentId());
        respVO.setPrivList(getService().listPrivs(user.getId()));
        return respVO;
    }

    /**
     * 登录
     */
    @Override
    public void login(AuthLoginReqVO reqVO) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(reqVO.getIdentity(), reqVO.getPassword());

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            Throwable cause = e.getCause();
            if (cause != null && cause instanceof BaseException) {
                throw (BaseException) cause;
            }
            long count = onLoginFail(reqVO.getIdentity());

            throw new BaseException(ResultCodeEnum.NAME_PASSWORD_ERROR);
        }

        onLoginSuccess(reqVO.getIdentity());

        redisSessionDao.save(subject.getSession(), reqVO.getIdentity());

    }

    /**
     * 登录失败处理
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public long onLoginFail(String identity) {
        User user = getService().getUser(identity);
        if (user == null) {
            return 0L;
        }

        loginLog(user, YesNoEnum.YES.getCode());

        // 计算一天的开始时间和结束时间
        long startTime = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
        long endTime = System.currentTimeMillis();

        return 1125;
    }

    /**
     * 登录成功处理
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void onLoginSuccess(String identity) {
        User user = getService().getUser(identity);
        if (user == null) {
            return;
        }

        loginLog(user, YesNoEnum.NO.getCode());

        resetLoginLog(user.getId());
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();

        if (subject.isAuthenticated()) {
            subject.logout();
        }
    }

    /**
     * 修改密码
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AuthResetPwdRespVO resetPassword(AuthRestPwdReqVO reqVO) {
        Long userId = ShiroUtil.getCurrentUserId();
        User user = userSrcMapper.selectByPrimaryKey(userId);

        String oldPassword = generatePassword(user.getSalt(), reqVO.getOldPassword());
        if (!user.getPassword().equals(oldPassword)) {
            return new AuthResetPwdRespVO(false);
        }

        String newPassword = generatePassword(user.getSalt(), reqVO.getNewPassword());
        user.setPassword(newPassword);
        user.setUpdateTime(new Date());
        userSrcMapper.updateByPrimaryKey(user);

        return new AuthResetPwdRespVO(true);
    }

    /**
     * 判断用户是否有权限
     */
    @Override
    public boolean hasPriv(String code) {
        return getService().listPrivs(ShiroUtil.getCurrentUserId()).contains(code);
    }

    /**
     * 判断用户是否有指定数据源的权限
     */
    @Override
    public boolean hasPriv(String code, String datasource) {
        List<RolePrivilege> rolePrivs = getService().listRolePrivs(ShiroUtil.getCurrentUserId());

        for (RolePrivilege rolePriv : rolePrivs) {
            if (!rolePriv.getPrivilegeCode().equals(code)) {
                continue;
            }
            if (datasource == null) {
                return true;
            }
            if (rolePriv.getDatasource().contains(datasource)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取用户角色权限信息
     */
    @Cacheable(value = "users", key = "'listRolePrivs_'+#id.toString()")
    @Override
    public List<RolePrivilege> listRolePrivs(Long id) {
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andUserIdEqualTo(id).andIsDeleteEqualTo(YesNoEnum.NO.getCode());

        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptyList();
        }

        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        RolePrivilegeExample rolePrivExample = new RolePrivilegeExample();
        rolePrivExample.createCriteria().andRoleIdIn(roleIds).andIsDeleteEqualTo(YesNoEnum.NO.getCode());

        return rolePrivilegeMapper.selectByExample(rolePrivExample);
    }

    /**
     * 获取用户权限
     */
    @Cacheable(value = "users", key = "'listPrivs_'+#id.toString()")
    @Override
    public Set<String> listPrivs(Long id) {
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andUserIdEqualTo(id).andIsDeleteEqualTo(YesNoEnum.NO.getCode());

        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptySet();
        }

        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        RolePrivilegeExample rolePrivExample = new RolePrivilegeExample();
        rolePrivExample.createCriteria().andRoleIdIn(roleIds).andIsDeleteEqualTo(YesNoEnum.NO.getCode());

        List<RolePrivilege> rolePrivs = rolePrivilegeMapper.selectByExample(rolePrivExample);
        if (CollectionUtils.isEmpty(rolePrivs)) {
            return Collections.emptySet();
        }

        Set<String> privSet = rolePrivs.stream().map(RolePrivilege::getPrivilegeCode).collect(Collectors.toSet());
        return splitPrivs(privSet);
    }

    /**
     * 统计用户权限数
     */
    @Cacheable(value = "users", key = "'countRoles_'+#id.toString()")
    @Override
    public Integer countRoles(Long id) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andIsDeleteEqualTo(YesNoEnum.NO.getCode()).andUserIdEqualTo(id);

        return (int) userRoleMapper.countByExample(example);
    }

    /**
     * 获取用户信息
     */
    @Cacheable(value = "users", key = "'get_'+#id.toString()")
    @Override
    public User getUser(Long id) {
        User user = userSrcMapper.selectByPrimaryKey(id);
        if (user == null) {
            return null;
        }
        return YesNoEnum.YES.getCode() == user.getIsDelete() ? null : user;
    }

    /**
     * 获取用户信息
     */
    @Cacheable(value = "users", key = "'get_'+#identity")
    @Override
    public User getUser(String identity) {
        UserExample example = new UserExample();
        example.createCriteria().andIdentityEqualTo(identity).andIsDeleteEqualTo(YesNoEnum.NO.getCode());

        List<User> users = userSrcMapper.selectByExample(example);
        User user = CollectionUtils.isEmpty(users) ? null : users.get(0);

        if (user != null) {
            userService.evictCache(user.getId());
        }
        return user;
    }

    /**
     * 登录IP校验
     */
    @Override
    public boolean checkIpValid(User user) {
        if (StringUtils.isEmpty(user.getIpLimit())) {
            return true;
        }

        String[] ips = user.getIpLimit().split(",");
        return false;
    }

    /**
     * 记录登录日志
     *
     * @param user
     * @param result
     */
    private void loginLog(User user, int result) {

    }

    /**
     * 分割权限码，将三级权限分割出一级、二级和三级
     *
     * @param privSet
     * @return
     */
    private Set<String> splitPrivs(Set<String> privSet) {
        Set<String> allPrivSet = new HashSet<>();

        for (String priv : privSet) {
            String[] items = priv.split("\\.");
            if (items.length != 3) {
                continue;
            }
            allPrivSet.add(items[0]);
            allPrivSet.add(items[0] + "." + items[1]);
            allPrivSet.add(priv);
        }
        return allPrivSet;
    }

    /**
     * 重置登录日志：清除输出密码的记录
     *
     * @param userId 用户id
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void resetLoginLog(Long userId) {

    }

    /**
     * 生成密码
     *
     * @param salt     盐值
     * @param password 密码
     * @return
     */
    private String generatePassword(String salt, String password) {
        return new SimpleHash("md5", password, salt, 1).toString();
    }

    /**
     * 获取 AuthService
     *
     * @return
     */
    private AuthService getService() {
        return ApplicationContextUtil.getClass(AuthService.class);
    }
}

package com.jackxueman.demo.service;


import com.jackxueman.demo.pojo.entity.RolePrivilege;
import com.jackxueman.demo.pojo.entity.User;
import com.jackxueman.demo.pojo.request.AuthLoginReqVO;
import com.jackxueman.demo.pojo.request.AuthRestPwdReqVO;
import com.jackxueman.demo.pojo.response.AuthLoginInfoRespVO;
import com.jackxueman.demo.pojo.response.AuthResetPwdRespVO;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

/**
 * 授权service
 */
public interface AuthService {

    /**
     * 获取用户登录信息
     *
     * @return
     */
    AuthLoginInfoRespVO getLoginInfo() throws UnsupportedEncodingException;

    /**
     * 登录
     *
     * @param reqVO
     */
    void login(AuthLoginReqVO reqVO);

    /**
     * 登录失败处理
     *
     * @param identity 身份证
     * @return 密码输错次数
     */
    long onLoginFail(String identity);

    /**
     * 登录成功处理
     *
     * @param identity 身份证
     */
    void onLoginSuccess(String identity);

    /**
     * 登出
     */
    void logout();

    /**
     * 重置登录日志：清除输出密码的记录
     *
     * @param userId 用户id
     */
    void resetLoginLog(Long userId);

    /**
     * 修改密码
     *
     * @param reqVO 原密码+新密码
     * @return
     */
    AuthResetPwdRespVO resetPassword(AuthRestPwdReqVO reqVO);

    /**
     * 判断用户是否有权限
     *
     * @param code 权限码
     * @return
     */
    boolean hasPriv(String code);

    /**
     * 判断用户是否有指定数据源的权限
     *
     * @param code       权限码
     * @param datasource 数据源
     * @return
     */
    boolean hasPriv(String code, String datasource);

    /**
     * 获取用户角色权限信息
     *
     * @param id 用户id
     * @return
     */
    List<RolePrivilege> listRolePrivs(Long id);

    /**
     * 获取用户权限
     *
     * @param id 用户id
     * @return
     */
    Set<String> listPrivs(Long id);

    /**
     * 统计用户权限数
     *
     * @param id
     * @return
     */
    Integer countRoles(Long id);

    /**
     * 获取用户信息
     *
     * @param id 用户id
     * @return
     */
    User getUser(Long id);

    /**
     * 获取用户信息
     *
     * @param identity 身份证
     * @return
     */
    User getUser(String identity);

    /**
     * 检查用户IP是否被限制
     *
     * @param user 用户信息
     * @return
     */
    boolean checkIpValid(User user);
}

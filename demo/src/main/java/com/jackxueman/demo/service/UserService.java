package com.jackxueman.demo.service;


import com.jackxueman.demo.pojo.entity.User;

/**
 * 用户service
 */
public interface UserService {

    /**
     * 清除用户缓存
     *
     * @param id 用户id
     */
    void evictCache(Long id);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int adduser(User user);
}

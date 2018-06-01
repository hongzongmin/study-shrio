package com.jackxueman.demo.service;


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
}

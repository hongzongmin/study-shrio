package com.jackxueman.demo.service.impl;

import com.jackxueman.demo.mapper.UserMapper;
import com.jackxueman.demo.pojo.entity.User;
import com.jackxueman.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户service实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private UserMapper userMapper;

    /**
     * 清除用户缓存
     */
    @Override
    public void evictCache(Long id) {
        Cache cache = cacheManager.getCache("users");
        if (cache == null) {
            return;
        }

        if (id == null) {
            cache.clear();
        } else {
            cache.evict("listRolePrivs_" + id);
            cache.evict("listPrivs_" + id);
            cache.evict("countRoles_" + id);
            cache.evict("get_" + id);
        }
    }

    @Override
    @Transactional
    public int adduser(User user) {
        return userMapper.insert(user);
    }

}

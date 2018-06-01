package com.jackxueman.demo.common.cache;

import org.springframework.data.redis.connection.RedisConnection;

import java.util.Set;

/**
 * redis service
 */
public interface RedisService {

    /**
     * 获取redis连接
     *
     * @return
     */
    RedisConnection getConnection();

    /**
     * 回收redis连接
     *
     * @param connection
     */
    void closeConnection(RedisConnection connection);

    /**
     * 判断key事由存在
     *
     * @param key key
     * @return
     */
    boolean exists(String key);

    /**
     * 获取字符串数据
     *
     * @param key key
     * @return
     */
    String get(String key);

    /**
     * 获取集合元素
     *
     * @param key key
     * @return
     */
    Set<String> sMembers(String key);

    /**
     * 缓存字符串数据
     *
     * @param key        key
     * @param value      value
     * @param expireTime 过期时间
     */
    void set(String key, String value, Integer expireTime);

    /**
     * 往集合中添加元素
     *
     * @param key        key
     * @param members    待添加元素
     * @param expireTime 过期时间
     */
    void sAdd(String key, Set<String> members, Integer expireTime);

    /**
     * 更新字符串数据过期时间
     *
     * @param key        key
     * @param expireTime 过期时间
     */
    void update(String key, Integer expireTime);

    /**
     * 删除字符串数据
     *
     * @param key key
     */
    void delete(String key);

    /**
     * 删除集合中的一个元素
     *
     * @param key     key
     * @param members 待移除元素
     */
    void sRem(String key, Set<String> members);
}

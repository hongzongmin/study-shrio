package com.jackxueman.demo.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * redise service实现类
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    /**
     * 获取Jedis资源
     */
    @Override
    public synchronized RedisConnection getConnection() {
        return jedisConnectionFactory.getConnection();
    }

    /**
     * 回收Jedis资源
     */
    @Override
    public void closeConnection(RedisConnection connection) {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * 判断key事由存在
     */
    @Override
    public boolean exists(String key) {
        RedisConnection connection = getConnection();
        if (connection != null) {
            return connection.exists(generateKey(key).getBytes());
        }
        return false;
    }

    /**
     * 获取字符串数据
     */
    @Override
    public String get(String key) {
        byte[] result = null;

        RedisConnection connection = getConnection();
        if (connection != null) {
            result = connection.get(generateKey(key).getBytes());
        }
        closeConnection(connection);

        if (result == null || result.length == 0) {
            return null;
        }
        return new String(result);
    }

    /**
     * 获取集合元素
     */
    @Override
    public Set<String> sMembers(String key) {
        Set<byte[]> result = null;

        RedisConnection connection = getConnection();
        if (connection != null) {
            result = connection.sMembers(generateKey(key).getBytes());
        }
        closeConnection(connection);

        if (result == null || result.isEmpty()) {
            return Collections.emptySet();
        }
        return result.stream().map(String::new).collect(Collectors.toSet());
    }

    /**
     * 缓存字符串数据
     */
    @Override
    public void set(String key, String value, Integer expireTime) {
        RedisConnection connection = getConnection();
        if (connection != null) {
            connection.set(generateKey(key).getBytes(), value.getBytes());

            if (!Objects.isNull(expireTime)) {
                connection.expire(generateKey(key).getBytes(), expireTime);
            }
        }
        closeConnection(connection);
    }

    /**
     * 往集合中添加元素
     */
    @Override
    public void sAdd(String key, Set<String> members, Integer expireTime) {
        RedisConnection connection = getConnection();
        if (connection != null) {
            byte[] keyByte = generateKey(key).getBytes();
            List<byte[]> valueBytes = members.stream().map(String::getBytes).collect(Collectors.toList());

            connection.sAdd(keyByte, valueBytes.toArray(new byte[][]{}));

            if (!Objects.isNull(expireTime)) {
                connection.expire(keyByte, expireTime);
            }
        }
        closeConnection(connection);
    }

    /**
     * 更新字符串数据过期时间
     */
    @Override
    public void update(String key, Integer expireTime) {
        RedisConnection connection = getConnection();
        if (connection != null) {
            if (!Objects.isNull(expireTime)) {
                connection.expire(generateKey(key).getBytes(), expireTime);
            }
        }
        closeConnection(connection);
    }

    /**
     * 删除字符串数据
     */
    @Override
    public void delete(String key) {
        RedisConnection connection = getConnection();
        if (connection != null) {
            connection.del(generateKey(key).getBytes());
        }
        closeConnection(connection);
    }

    /**
     * 删除集合中的一个元素
     */
    @Override
    public void sRem(String key, Set<String> members) {
        RedisConnection connection = getConnection();
        if (connection != null) {
            List<byte[]> valueBytes = members.stream().map(String::getBytes).collect(Collectors.toList());
            connection.sRem(generateKey(key).getBytes(), valueBytes.toArray(new byte[][]{}));
        }
        closeConnection(connection);
    }

    /**
     * 生成redis key
     *
     * @param key
     * @return
     */
    private String generateKey(String key) {
        return String.join("_", key);
    }
}

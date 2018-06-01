package com.jackxueman.demo.auth;

import com.jackxueman.demo.common.cache.RedisService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * redis session manager
 */
@Component
public class RedisSessionDao {

    @Value("${session.expire.time}")
    private int expireTime;

    @Value("${session.key.prefix}")
    private String keyPrefix;

    @Autowired
    private RedisService redisService;

    /**
     * 新建session
     *
     * @param session  session
     * @param identity 身份证
     */
    public void save(Session session, String identity) {
        String key = generateKey(identity);
        String oldSessionId = this.read(key);
        if (oldSessionId != null) {
            delete(identity);
        }
        session.setTimeout(expireTime * 1000);

        redisService.set(key, session.getId().toString(), expireTime);
    }

    /**
     * 删除session
     *
     * @param identity 身份证
     */
    public void delete(String identity) {
        String key = generateKey(identity);
        redisService.delete(key);
    }

    /**
     * 更新session超时时间
     *
     * @param identity 身份证
     */
    public void update(Session session, String identity) {
        String key = generateKey(identity);

        session.setTimeout(expireTime * 1000);
        redisService.update(key, expireTime);
    }

    /**
     * 读取session id
     *
     * @param identity 身份证
     * @return
     */
    public String read(String identity) {
        String key = generateKey(identity);
        return redisService.get(key);
    }

    /**
     * 生成session key
     *
     * @param identity
     * @return
     */
    private String generateKey(String identity) {
        return String.join("_", keyPrefix, identity);
    }
}
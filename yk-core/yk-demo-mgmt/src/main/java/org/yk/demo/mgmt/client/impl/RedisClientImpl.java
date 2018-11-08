package org.yk.demo.mgmt.client.impl;


import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yk.demo.mgmt.client.RedisClient;

import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisClientImpl implements RedisClient{

    private final static int DEFAULT_ADDLOCK_RETRY_INTERVAL = 100;
    
    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 删除key
     * @param key
     */
    @Override
    public void remove(final Serializable key) {
        redisTemplate.delete(key);
    }
    
    /**
     * 批量删除key
     * @param keys
     */
    @Override
    public void removeKeys(final Set<Serializable> keys) {
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }
    
    /**
     * 根据通配符pattern，批量删除key
     * @param pattern
     */
    @Override
    public void removePattern(final Serializable pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * 
     * @param key
     * @return
     */
    @Override
    public boolean exists(final Serializable key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * getStr
     * @param key
     * @return
     */
    @Override
    public Object getStr(final Serializable key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * setStr
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean setStr(final Serializable key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    /**
     * add into cache with expiration time
     * 
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean setStr(final Serializable key, Object value, Long expireTime) {
        try {
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    @Override
    public boolean addLock(String key, Long lockTime, int retryCount) {
        try {
            if (this.setIfAbsent(key, key + "LOCK",lockTime)) {
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        try {
            Thread.sleep(DEFAULT_ADDLOCK_RETRY_INTERVAL);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        int count = retryCount;
        while (count > 0) {
            if (this.setIfAbsent(key, key + "LOCK",lockTime)) {
                return true;
            }
            try {
                Thread.sleep(DEFAULT_ADDLOCK_RETRY_INTERVAL);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            count--;
        }
        return false;
    }

    @Override
    public void deleteRedisLock(String key) {
        redisTemplate.delete(key);
    }
    
    
    private Boolean setIfAbsent(String key,String value,Long secounds){
        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        byte[] rawKey = serializer.serialize(key);
        byte[] rawValue = serializer.serialize(value);
        byte[] ex = serializer.serialize("EX");
        byte[] nx = serializer.serialize("NX");
        byte[] expire = serializer.serialize(String.valueOf(secounds));
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
               return (String) connection.execute("set", new byte[][]{
                    rawKey,rawValue,ex,expire,nx});
            }
        }, true);
        return "OK".equals(result);
    }
    
}

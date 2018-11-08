package org.yk.demo.mgmt.client;

import java.io.Serializable;
import java.util.Set;

public interface RedisClient {

    void remove(Serializable key);

    void removeKeys(Set<Serializable> keys);

    void removePattern(Serializable pattern);

    boolean exists(Serializable key);

    Object getStr(Serializable key);

    boolean setStr(Serializable key, Object value);

    boolean setStr(Serializable key, Object value, Long expireTime);

    boolean addLock(String key, Long lockTime, int retryCount);

    void deleteRedisLock(String key);

}

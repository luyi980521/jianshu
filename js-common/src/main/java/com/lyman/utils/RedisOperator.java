package com.lyman.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by luyi on 2020/12/27.
 * 描述：Redis 操作工具类
 */
@Component
public class RedisOperator {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /****************** 简单的 key-value 操作 ******************/

    /**
     * 判断 key 是否存在
     *
     * @param key key
     * @return true / false
     */
    public boolean keyIsExist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 实现命令：TTL（time to live） key，以秒为单位，返回给定 key 的剩余有效时间
     *
     * @param key key
     * @return 剩余有效时间
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 实现命令：expire，为给定 key 设置过期时间，单位为秒
     *
     * @param key 给定 key
     * @param timeout 要设置的过期时间
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 实现命令：increment key，增加 key 一次
     *
     * @param key key
     * @param delta
     * @return 新增成功后 key 对应的值
     */
    public long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 实现命令：decrement key，减少 key 一次
     */
    public long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 实现命令：keys pattern，查看所有符合 pattern 的 key
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 实现命令：del key，删除一个 key
     *
     * @param key key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 实现命令：set key value，对给定的 key 的值进行修改
     *
     * @param key 给定的 key
     * @param value 需要修改的值
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 实现命令：set key value EX seconds，设置 key 并设置超时时间
     *
     * @param key key
     * @param value value
     * @param timeout 超时时间
     */
    public void set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 如果 key 不存在，则设置，如果存在，则报错
     *
     * @param key key
     * @param value value
     */
    public void setnx60s(String key, String value) {
        redisTemplate.opsForValue().setIfAbsent(key, value, 60, TimeUnit.SECONDS);
    }

    /**
     * 如果 key 不存在，则设置，如果 key 存在，则报错
     *
     * @param key key
     * @param value value
     */
    public void setnx(String key, String value) {
        redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 实现命令：get key，返回 key 所关联的字符串值
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 实现命令：mget，批量查询
     */
    public List<String> mget(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 批量查询，管道 pipeline
     */
    public List<Object> batchGet(List<String> keys) {

        return redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {

                StringRedisConnection conn = (StringRedisConnection) redisConnection;

                for (String key : keys) {
                    conn.get(key);
                }

                return null;
            }
        });
    }



    /****************** hash ************************/

    /**
     * 实现命令：hset key field value，将哈希表 key 中的 field 的值设置为 value
     */
    public void hset(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 实现命令：hget key field，返回哈希表 key 中的 field 的值
     */
    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 实现命令：hdel key field [field...]，删除哈希表中 key 的一个或多个值，
     * 不存在的将被忽略
     */
    public void hdel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 实现命令：hgetall key，返回哈希表 key 中所有的值
     */
    public Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }



    /************************* list *************************/

    /**
     * 实现命令：lpush key value，将 value 插入到列表 key 的表头
     *
     * @param key key
     * @param value
     * @return 插入成功后列表的长度
     */
    public long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 实现命令：lpop key，移除 key 这个列表的头元素
     *
     * @param key key
     * @return 被删除了的头结点
     */
    public String lpop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 实现命令：rpush key value，将 value 插入到列表 key 的尾部（最右边）
     */
    public long rpush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }
}

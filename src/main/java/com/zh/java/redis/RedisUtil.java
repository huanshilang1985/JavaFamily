package com.zh.java.redis;

import com.beust.jcommander.ParameterException;
import com.zh.java.util.DateUtil;
import com.zh.java.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author zhanghe
 * @Desc:
 * @Date 2019/4/23 17:37
 */
@Slf4j
public class RedisUtil {

    private static String DEFAULT_CHARSET = "UTF-8";
    public static final String KEY_ORDER_NO = "KEY_ORDER_NO"; /* 订单号 */

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 存放信息,支持覆盖
     *
     * @param key
     *            存放key
     * @param value
     *            存放值
     * @return
     */
    public boolean save(String key, String value) {
        return save(key, value, 0);
    }

    /**
     * 存放信息,支持覆盖
     *
     * @param key
     *            存放key
     * @param value
     *            存放值
     * @param time
     *            过期时间，单位秒
     * @return
     */
    public boolean save(String key, String value, long time) {
        byte[] values = null;
        try {
            values = value.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.info("保存时，value转换utf-8编码异常，key【{}】，value【{}】", key, value);
            throw new ParameterException("保存时，value转换utf-8编码异常，key{" + key + "}，value{" + value + "}");
        }
        return save(key.getBytes(), values, time);
    }

    /**
     * 存放信息,支持覆盖、过期时间
     *
     * @param key
     *            存放key
     * @param value
     *            存放值
     * @param time
     *            过期时间,单位秒
     * @return
     */
    public boolean save(final byte[] key, final byte[] value, final long time) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.set(key, value);
                    if (time > 0) {
                        connection.expire(key, time);
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的save方法出现异常！");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取存放在redis中的值
     *
     * @param key
     *            存放key
     * @return
     */
    public String query(String key) {
        byte[] value = qurey(key.getBytes());
        if (value == null) {
            log.info("根据【{}】获取值【{}】", key, value);
            return "";
        } else {
            try {
                return new String(value, DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                log.info("操作Redis的query方法出现异常！，value转换utf-8编码异常，key【{}】，value【{}】", key, value);
                return "";
            }
        }
    }

    /**
     * 获取存放在redis中的值
     *
     * @param key
     *            存放key
     * @return
     */
    public byte[] qurey(final byte[] key) {
        try {
            return redisTemplate.execute(new RedisCallback<byte[]>() {
                public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.get(key);
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的qurey方法出现异常！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取与字段中存储的键哈希相关联的值
     *
     * @param key
     * @param field
     * @return
     */
    public String hQuery(final String key, final String field) {
        try {
            return redisTemplate.execute(new RedisCallback<String>() {
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] value = connection.hGet(key.getBytes(), field.getBytes());
                    return value.toString();
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的queryKeys方法出现异常！");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 查询匹配的所有KEY
     *
     * @param pattern
     * @return
     */
    public List<String> queryKeys(final String pattern) {
        try {
            return redisTemplate.execute(new RedisCallback<List<String>>() {
                public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                    Set<byte[]> value = connection.keys(pattern.getBytes());
                    if (value != null && !value.isEmpty()) {
                        List<String> values = new ArrayList<String>();
                        for (byte[] v : value) {
                            try {
                                values.add(new String(v, DEFAULT_CHARSET));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        return values;
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的queryKeys方法出现异常！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断键值是否存在
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.exists(key.getBytes());
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的exists方法出现异常！");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断键值是否存在
     *
     * @param key
     * @return
     */
    public boolean exists(final byte[] key) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.exists(key);
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的exists方法出现异常！");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 模糊查詢所有的key,模糊查询 (key*)
     *
     * @param keys
     * @return
     */
    public Set<byte[]> queryByVague(final String keys) {
        try {
            return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
                public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.keys(keys.getBytes());
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的queryByVague方法出现异常！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除在redis中的值
     *
     * @param key
     *            存放key
     * @return
     */
    public Long delete(final String key) {
        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.del(key.getBytes());
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的delete方法出现异常！");
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 删除在redis中的值
     *
     * @param key
     *            存放key
     * @return
     */
    public Long delete(final byte[] key) {
        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.del(key);
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的delete方法出现异常！");
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 删除在redis中的值
     *
     * @param keys 存放key
     * @return
     */
    public Long delete(final Set<byte[]> keys) {
        long count = 0;
        for (byte[] key : keys) {
            count = count + delete(key);
        }
        return count;
    }

    /**
     * 增量获取redis中的值,支持数字
     *
     * @param key 存放key
     * @param incrementValue 增量值
     * @return
     */
    public Long increment(String key, long incrementValue) {
        try {
            BoundValueOperations<String, String> bo = redisTemplate.boundValueOps(key);
            if (bo == null) {
                return 0L;
            } else {
                return bo.increment(incrementValue);
            }
        } catch (Exception e) {
            log.info("操作Redis的increment方法出现异常！");
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 增量获取redis中的值,每次增长1
     *
     * @param key 存放key
     * @return
     */
    public Long increment(String key) {
        try {
            BoundValueOperations<String, String> bo = redisTemplate.boundValueOps(key);
            if (bo == null) {
                return 0L;
            } else {
                return bo.increment(1);
            }
        } catch (Exception e) {
            log.info("操作Redis的increment方法出现异常！");
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    public boolean flushDB() {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.flushDb();
                    return true;
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的flushDB方法出现异常！");
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Redis从List头部存储数据
     *
     * @param key
     * @param value
     * @return 当前List的长度
     */
    public Long lpush(final String key, final String value) {
        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.lPush(key.getBytes(), value.getBytes());
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的lpush方法出现异常！");
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * Redis从List尾部提取数据
     *
     * @param key
     * @return value
     */
    public String rPop(final String key) {
        try {
            return redisTemplate.execute(new RedisCallback<String>() {
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] re = connection.rPop(key.getBytes());
                    String value = "";
                    if (re != null && re.length > 0) {
                        try {
                            value = new String(re, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    return value;
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的rPop方法出现异常！");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取List的长度
     *
     * @param key
     * @return
     */
    public Long lLen(final String key) {
        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.lLen(key.getBytes());
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的lLen方法出现异常！");
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key
     * @param field
     *            哈希表的字段名
     * @return
     */
    public String hGet(final String key, final String field) {
        try {
            return redisTemplate.execute(new RedisCallback<String>() {
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    String result = "";
                    byte[] values = connection.hGet(key.getBytes(), field.getBytes());
                    if (values == null) {
                        return result;
                    } else {
                        try {
                            result = new String(values, DEFAULT_CHARSET);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    return result;
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的hGet方法出现异常！");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取在哈希表中指定 KEY的所有字段和值
     *
     * @param key
     * @return
     */
    public Map<String, String> hGetAll(final String key) {
        try {
            return redisTemplate.execute(new RedisCallback<Map<String, String>>() {
                public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                    Map<byte[], byte[]> values = connection.hGetAll(key.getBytes());
                    Map<String, String> map = new HashMap<String, String>();
                    if (values != null && !values.isEmpty()) {
                        Set<byte[]> keys = values.keySet();
                        if (keys != null && !keys.isEmpty()) {
                            for (byte[] key : keys) {
                                try {
                                    map.put(new String(key, DEFAULT_CHARSET), new String(values.get(key), DEFAULT_CHARSET));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    log.info("Redis提取哈希表, key【{}】, value【{}】", key, JsonUtil.objectToJson(map));
                    return map;
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的hGetAll方法出现异常！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     *
     * @param key
     *            键
     * @param field
     *            哈希的字段名
     * @param value
     *            哈希的值
     * @return
     */
    public Boolean hSet(final String key, final String field, final String value) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    Boolean result = false;
                    try {
                        result = connection.hSet(key.getBytes(), field.getBytes(DEFAULT_CHARSET), value.getBytes(DEFAULT_CHARSET));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的hSet方法出现异常！");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     *
     * @param key
     * @param map
     * @return
     */
    public Boolean hMSet(final String key, final Map<String, String> map) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    Boolean result = false;
                    try {
                        Map<byte[], byte[]> hashes = new HashMap<byte[], byte[]>();
                        if (map != null && !map.isEmpty()) {
                            Set<String> setKeys = map.keySet();
                            if (setKeys != null && !setKeys.isEmpty()) {
                                for (String skey : setKeys) {
                                    hashes.put(skey.getBytes(DEFAULT_CHARSET), map.get(skey).getBytes(DEFAULT_CHARSET));
                                }
                            }
                        }
                        connection.hMSet(key.getBytes(), hashes);
                        result = true;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    log.info("插入哈希表，key【{}】, value【{}】, 操作结果【{}】", key, JsonUtil.objectToJson(map), result);
                    return result;
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的hMSet方法出现异常！");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除一个哈希表字段
     *
     * @param key
     * @param field
     *            哈希表字段
     * @return
     */
    public Long hDel(final String key, final String field) {
        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.hDel(key.getBytes(), field.getBytes());
                }
            });
        } catch (Exception e) {
            log.info("操作Redis的hDel方法出现异常！");
            e.printStackTrace();
        }
        return 0L;
    }
}

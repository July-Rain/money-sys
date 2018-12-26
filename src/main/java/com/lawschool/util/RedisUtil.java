package com.lawschool.util;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-17 21:12
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;
    private final static Gson gson = new Gson();

    public void set(String key, Object value, long expire){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }




    public <T> T get(String key, Class<T> clazz, long expire) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String value = String.valueOf(valueOperations.get(key));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String value = String.valueOf(valueOperations.get(key));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return gson.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }

    /**
     * @Author MengyuWu
     * @Description 判断key是否存在
     * @Date 16:28 2018-12-15
     * @Param [key]
     * @return boolean
     **/
    
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取流水号
     * @param key key值
     * @param expire 过期时间，-1不过期
     * @param length 编号长度
     * @return
     */
    public String getNumber(String key, long expire, Integer length){
        String result = "";// 返回结果
        if(length == null){
            length = 4;
        }

        synchronized(this) {
            String value = this.get(key);

            if(StringUtils.isBlank(value) || "null".equals(value)){// 无此key对应值，保存初始值

                result = String.format("%0"+length+"d", 1);

                this.set(key, String.format("%0"+length+"d", 2), expire);
            } else {
                result = value;
                Integer temp = Integer.parseInt(value);
                temp++;
                this.set(key, String.format("%0"+length+"d", temp));
            }
        }

        return result;
    }
}

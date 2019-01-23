package com.xlizy.middleware.cc.server.service.impl;

import com.xlizy.middleware.cc.server.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author xlizy
 * @date 2018/4/25
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    protected RedisTemplate redisTemplate;

    @Override
    public void clearSessionCache() {
        Set<String> keys = redisTemplate.keys("spring:session:cc:*");
        redisTemplate.delete(keys);
    }

    @Override
    public void clearDataCache() {
        Set<String> keys = redisTemplate.keys("*");
        Set<String> ignore = redisTemplate.keys("spring:session:cc:*");
        keys.removeAll(ignore);
        redisTemplate.delete(keys);
    }
}

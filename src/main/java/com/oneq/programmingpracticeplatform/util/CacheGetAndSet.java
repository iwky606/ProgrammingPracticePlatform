package com.oneq.programmingpracticeplatform.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CacheGetAndSet {
    @Resource
    RedisTemplate<String, Object> redisTemplate;


}

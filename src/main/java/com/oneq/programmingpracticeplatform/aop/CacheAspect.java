package com.oneq.programmingpracticeplatform.aop;

import cn.hutool.json.ObjectMapper;
import com.oneq.programmingpracticeplatform.annotation.Cache;
import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(cache)")
    public Object cache(ProceedingJoinPoint joinPoint, Cache cache) throws Throwable {
        String key = generateKey(cache.key(), joinPoint.getArgs());
        Object cachedValue = redisTemplate.opsForValue().get(key);

        if (cachedValue != null) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?> returnType = method.getReturnType();
            if (returnType.isInstance(cachedValue)) {
                return returnType.cast(cachedValue);
            }
            log.info(key);
            log.info((String) cachedValue);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "缓存转换失败");
        }

        Object result = joinPoint.proceed();
        redisTemplate.opsForValue().set(key, result, cache.expire(), cache.timeUnit());

        return result;
    }

    private String generateKey(String keyTemplate, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            keyTemplate = keyTemplate.replace("{" + i + "}", String.valueOf(args[i]));
        }
        return keyTemplate;
    }

}
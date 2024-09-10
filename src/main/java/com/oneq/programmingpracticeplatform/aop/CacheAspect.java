package com.oneq.programmingpracticeplatform.aop;

import com.oneq.programmingpracticeplatform.annotation.Cache;
import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Autowired
    @Qualifier("objectRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private Random random;

    @Around("@annotation(cache)")
    public Object cache(ProceedingJoinPoint joinPoint, Cache cache) throws Throwable {
        String key = generateKey(cache.key(), joinPoint.getArgs());
        log.info(key);
        Object cachedValue = redisTemplate.opsForValue().get(key);

        if (cachedValue != null) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?> returnType = method.getReturnType();
            log.info(returnType.getTypeName());
            if (returnType.isInstance(cachedValue)) {
                return returnType.cast(cachedValue);
            }
            log.info(key);
            log.info((String) cachedValue);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "缓存转换失败");
        }

        Object result = joinPoint.proceed();
        // 统一换算成ms, 添加上随机时间
        long expire = cache.timeUnit().toMillis(cache.expire()) + TimeUnit.SECONDS.toMillis(getRandTime(cache.randTime()));
        log.info("expire time:" + expire);
        redisTemplate.opsForValue().set(key, result, expire, TimeUnit.MILLISECONDS);
        return result;
    }

    private String generateKey(String keyTemplate, Object[] args) {
        StringBuilder sb = new StringBuilder(keyTemplate);
        for (int i = 0; i < args.length; i++) {
            sb.append("." + args[i]);
        }
        return sb.toString();
    }

    private long getRandTime(int bound) {
        return random.nextInt(bound);
    }
}
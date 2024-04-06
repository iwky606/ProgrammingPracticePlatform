package com.oneq.programmingpracticeplatform.aop;

import com.oneq.programmingpracticeplatform.annotation.AuthCheck;
import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.vo.UserVo;
import com.oneq.programmingpracticeplatform.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class AuthAspect {
    @Resource
    UserService userService;

    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        int mustRole = authCheck.mustRole();
        if (mustRole == 0) {
            // 装饰器使用错误
            throw new RuntimeException();
        }

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 当前登录用户
        UserVo loginUser = userService.getLoginUser(request);

        // admin所有的权限都通过
        if (loginUser.getAuth().equals(AuthEnum.ADMIN)) {
            return joinPoint.proceed();
        }

        AuthEnum needAuth = AuthEnum.getEnumByValue(mustRole);
        if (loginUser.getAuth().equals(needAuth)) {
            return joinPoint.proceed();
        }

        throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "权限不足");
    }
}

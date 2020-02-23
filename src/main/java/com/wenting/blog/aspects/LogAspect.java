package com.wenting.blog.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {

    private RequestLog requestLog;

    @Pointcut("execution(* com.wenting.blog.controller.*.*(..))")
    public void log() {}

    @Before("com.wenting.blog.aspects.LogAspect.log()")
    public void logBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        requestLog = new RequestLog(url, ip, classMethod, args);
        long time = System.currentTimeMillis();
        log.info("[请求开始]----requestLog={} ,\r\n [请求开始时间]----time={}", requestLog,time);

    }

    @After("com.wenting.blog.aspects.LogAspect.log()")
    public void logAfter(){
//        log.info("--------- logAfter----");
    }

    @AfterReturning(value = "com.wenting.blog.aspects.LogAspect.log()", returning = "result")
    public void logReturn(Object result){
        long time = System.currentTimeMillis();
        log.info("[请求结束] requestLog={}, \r\n [请求结束时间]----time={}", requestLog, time);
    }







}

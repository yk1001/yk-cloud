package org.yk.scheduler.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {
	
    @Pointcut("execution(public * org.yk.scheduler.controller.*.*(..))")
    public void log() {}
 
    /*@Before注解表示在具体的方法之前执行*/
    @Around("log()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
    	Object[] args = pjp.getArgs();
    	String className = pjp.getTarget().getClass().getName();
    	String methodName = pjp.getSignature().getName();
    	log.info("class:{},methodName:{},args:{}",className,methodName,args);
    	Long startTimeMillis = System.currentTimeMillis();
    	Object resp	= pjp.proceed();
    	Long endTimeMillis = System.currentTimeMillis();
    	Long spend = endTimeMillis - startTimeMillis;
		log.info("class:{},methodName:{},spend:{} ms,resp:{}",className,methodName,spend,resp);
    }
 
}
package org.yk.demo.mgmt.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * controller 切面拦截
 * 
 * */
@Aspect
@Component
@Slf4j
public class ControllerLogAspect {
	
	private  final static Long EXPECT_MAX_HANDLE_TIME = 2 * 1000L; // 2 s
	private  final static String CONTROLLER_BASE_PACKAGE = "org.yk.demo.mgmt.controller";

    @Pointcut("execution(public * "+CONTROLLER_BASE_PACKAGE+".*.*(..))")
    public void controllerLog() {}
 
    /*@Before注解表示在具体的方法之前执行*/
    @Around("controllerLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
    	Object[] args = pjp.getArgs();
    	String className = pjp.getTarget().getClass().getName();
    	String methodName = pjp.getSignature().getName();
    	log.info("class:{},methodName:{},args:{}",className,methodName,args);
    	Long startTimeMillis = System.currentTimeMillis();
    	Object resp	= pjp.proceed();
    	Long endTimeMillis = System.currentTimeMillis();
    	Long spend = endTimeMillis - startTimeMillis;
		log.info("class:{},methodName:{},spend:{} ms,resp:{}",className,methodName,spend,resp);
		if(spend > EXPECT_MAX_HANDLE_TIME){
		        log.warn("class:{},methodName:{},spend:{}",className,methodName,spend);
		}
		return resp;
    }
 
}
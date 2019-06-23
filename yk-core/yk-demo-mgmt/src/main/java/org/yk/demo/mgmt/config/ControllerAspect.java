package org.yk.demo.mgmt.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.yk.common.constant.ResultCode;
import org.yk.common.data.GeneralContentResult;
import org.yk.common.data.GeneralPagingResult;
import org.yk.common.data.GeneralResult;
import org.yk.common.error.GeneralContentException;
import org.yk.common.error.GeneralException;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

/**
 * controller 切面拦截
 * 
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    private final static Long EXPECT_MAX_HANDLE_TIME = 2 * 1000L; // 2 s
    private final static String CONTROLLER_BASE_PACKAGE = "org.yk.demo.mgmt.controller";

    @Pointcut("execution(public * " + CONTROLLER_BASE_PACKAGE + "..*.*(..))")
    public void controller() {
    }

    /**
     * 打印输入输出及处理时间过长的日志
     * 针对异常的统一处理
     * */
    @Around("controller()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        log.info("class:{},methodName:{},args:{}", className, methodName, args);
        Long startTimeMillis = System.currentTimeMillis();
        Object resp =  null;
        try {
            resp = pjp.proceed();
        } catch (Exception e) {
            MethodSignature ms = (MethodSignature) pjp.getSignature();
            Class returnType = ms.getMethod().getReturnType();
            resp = handleException(returnType,e) ;
        }
        Long endTimeMillis = System.currentTimeMillis();
        Long spend = endTimeMillis - startTimeMillis;
        log.info("class:{},methodName:{},spend:{} ms,resp:{}", className, methodName, spend, resp);
        if (spend > EXPECT_MAX_HANDLE_TIME) {
            log.warn("class:{},methodName:{},spend:{}", className, methodName, spend);
        }
        return resp;
    }

    /**
     * 统一的异常处理
     * */
    private GeneralResult handleException(Class returnType,Exception e) throws Throwable{
        GeneralResult errorResult = null;
        if(returnType.equals(GeneralContentResult.class)
                || returnType.equals(GeneralPagingResult.class)){
            errorResult = new GeneralContentResult();
        } else if(returnType.equals(GeneralResult.class)){
            errorResult = new GeneralResult();
        } else {
            throw e;
        }
        if(e instanceof GeneralException){
            errorResult.setResultCode(((GeneralException) e).getErrorCode());
            errorResult.setDetailDescription(((GeneralException) e).getDetailDescription());
        }
        if(e instanceof GeneralContentException && errorResult instanceof GeneralContentResult){
            ((GeneralContentResult)errorResult).setResultContent(((GeneralContentException) e).getResultContent());
        }
        if(StringUtils.isBlank(errorResult.getResultCode())){
            log.error(e.getMessage(),e);
            errorResult.setResultCode(ResultCode.SERVER_UNAVALIABLE);
            errorResult.setDetailDescription(e.getMessage());
        }
        return errorResult;
    }
}
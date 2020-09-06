package com.silverhyuk.currencyconverter.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 로깅용 AOP
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 소요시간 로깅
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.silverhyuk.currencyconverter.common.annotation.ProgressTime)")
    public Object commonServiceLogging(ProceedingJoinPoint joinPoint) throws Throwable {

        long currentTime = System.currentTimeMillis();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.debug("=================================================");
        log.debug(">>>>>>>>> LOGGING START >>>>>>>>>>");
        log.debug("[class]:{}", className);
        log.debug("[method]:{}()", methodName);

        Object result = joinPoint.proceed();

        log.debug("[소요시간]: {}ms", new Object[]{(System.currentTimeMillis()-currentTime)});
        log.debug(">>>>>>>>>> LOGGING END >>>>>>>>>>");
        log.debug("=================================================");

        return result;
    }

    @Pointcut("execution(public * com..*Controller.*(..))")
    public void controllerClassMethods() {}

    @Before(value = "controllerClassMethods()")
    public void checkSessionValid(JoinPoint joinPoint) {
        String token = joinPoint.getSignature().getDeclaringTypeName();
        log.debug("Controller Before AOP >>>>>>>>>>>>>>>  {}", token);
    }
}

package com.fundgroup.backend.logger;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;



@Aspect
@Component
@Log4j2
public class CustomLogger {
    
    @Around("@annotation(com.fundgroup.backend.customAnnotation.LogExecutionTime)")
    @SneakyThrows
    public Object logAroundScheduleTask(ProceedingJoinPoint pjp){
        final long start = System.currentTimeMillis();
        var proceed=pjp.proceed();
        final long executionTime = System.currentTimeMillis() - start;
        System.out.println(pjp.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }

}

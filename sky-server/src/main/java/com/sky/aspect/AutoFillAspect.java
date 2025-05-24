package com.sky.aspect;


import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill) ")
    public void autoPointCut(){

    }


    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("autoPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始到切入点了");

        //获取被拦截的数据类型，就是@AutoFill(Update)
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType=annotation.value();

        //获取实体参数
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }
        Object entity = args[0];
        //为这些实体属性准备好数据
        LocalDateTime now=LocalDateTime.now();
        Long UserId= BaseContext.getCurrentId();

        //为操作通过反射赋值
        if(operationType==OperationType.INSERT){
            //先获得再赋值
            Method create_time = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method create_user = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            Method update_time = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method update_user = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            create_time.invoke(entity,now);
            create_user.invoke(entity,UserId);
            update_user.invoke(entity,UserId);
            update_time.invoke(entity,now);

        } else if (operationType==OperationType.UPDATE) {
            Method update_time = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method update_user = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            update_user.invoke(entity,UserId);
            update_time.invoke(entity,now);
        }

    }
}

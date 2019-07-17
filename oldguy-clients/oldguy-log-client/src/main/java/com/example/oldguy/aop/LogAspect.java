package com.example.oldguy.aop;

import com.alibaba.fastjson.JSON;
import com.example.oldguy.annonations.LogControl;
import com.example.oldguy.model.constans.AspectConstants;
import com.example.oldguy.model.dto.AppLogAddForm;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.services.AppLogApi;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;


/**
 * @ClassName: PermsAspect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 9:01
 **/
@Aspect
@Order(AspectConstants.LOG_ORDER)
public class LogAspect {

    @Autowired
    private AppLogApi appLogApi;

    @Pointcut("@annotation(com.example.oldguy.annonations.LogControl)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogControl logControl = signature.getMethod().getAnnotation(LogControl.class);


        RspMsg rsp = appLogApi.save(
                new AppLogAddForm(
                        logControl.type().getCode(),
                        logControl.message(),
                        null
                )
        );

        System.out.println(JSON.toJSON(rsp));
    }
}

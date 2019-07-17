package com.example.oldguy.aop;

import com.example.oldguy.annonations.HasPerms;
import com.example.oldguy.exceptions.PermException;
import com.example.oldguy.model.constans.AspectConstants;
import com.example.oldguy.model.constans.HttpStatus;
import com.example.oldguy.model.dto.PermsForm;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.services.PermClientApi;
import com.example.oldguy.services.UserSessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: PermsAspect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 9:01
 **/
@Aspect
@Order(AspectConstants.PERM_ORDER)
public class PermsAspect {

    @Autowired
    private PermClientApi permClientApi;

    @Pointcut("@annotation(com.example.oldguy.annonations.HasPerms)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        HasPerms hasPerms = signature.getMethod().getAnnotation(HasPerms.class);

        PermsForm form = new PermsForm();
        form.setUserId(UserSessionUtils.getJwtInfo().getUserId());
        Set<String> roleSet = new HashSet<>();
        for (String role : hasPerms.roles()) {
            if (StringUtils.isNotBlank(role)) {
                roleSet.add(role);
            }
        }
        form.setRoles(roleSet);
        boolean logicalAnd = hasPerms.logical().equals(HasPerms.Logical.AND) ? true : false;
        form.setLogicalAnd(logicalAnd);

        RspMsg rsp = permClientApi.hasPerms(form);

        if (!rsp.getCode().equals(HttpStatus.OK.value())) {
            throw new PermException(rsp.getMessage(), rsp.getCode());
        }
    }
}

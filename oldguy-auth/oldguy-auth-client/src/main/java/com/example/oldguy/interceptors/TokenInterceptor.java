package com.example.oldguy.interceptors;

import com.alibaba.fastjson.JSON;
import com.example.oldguy.configs.AutoAuthClientConfiguration;
import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.services.UserSessionUtils;
import com.example.oldguy.utils.Log4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: TokenInterceptor
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/14 0014 下午 3:05
 **/
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AutoAuthClientConfiguration.JWT_INFO_NAME);
        if (StringUtils.isNotBlank(token)) {
            Log4jUtils.getInstance(getClass()).debug("解析token");

            byte[] decode = Base64Utils.decodeFromString(token);
            JwtInfo info = JSON.parseObject(new String(decode, "UTF-8"), JwtInfo.class);

            UserSessionUtils.pushJwtToken(token);
            UserSessionUtils.pushJwtInfo(info);

        } else {
            Log4jUtils.getInstance(getClass()).warn("没有获取到token信息");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserSessionUtils.remove();
    }
}

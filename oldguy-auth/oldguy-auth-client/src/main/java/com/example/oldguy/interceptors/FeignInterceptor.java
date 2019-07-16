package com.example.oldguy.interceptors;

import com.example.oldguy.configs.AutoAuthClientConfiguration;
import com.example.oldguy.services.UserSessionUtils;
import com.example.oldguy.utils.Log4jUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @ClassName: FeignInterceptor
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 1:44
 **/
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Log4jUtils.getInstance(getClass()).debug("feign 转发 token");
        template.header(AutoAuthClientConfiguration.JWT_INFO_NAME, UserSessionUtils.getJwtToken());
    }
}

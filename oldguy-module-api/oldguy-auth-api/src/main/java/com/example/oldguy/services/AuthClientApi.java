package com.example.oldguy.services;

import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.model.dto.RspMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName: AuthClientApi
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/9 0009 下午 8:16
 **/
@FeignClient(value = "auth-server/auth")
public interface AuthClientApi {

    @ApiOperation("校验token")
    @GetMapping("check/{token}")
    RspMsg<JwtInfo> checkToken(@PathVariable("token") String token);

}

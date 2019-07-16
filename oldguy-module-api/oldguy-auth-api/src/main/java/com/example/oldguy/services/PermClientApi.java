package com.example.oldguy.services;

import com.example.oldguy.model.dto.PermsForm;
import com.example.oldguy.model.dto.RspMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName: PermClientApi
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 9:13
 **/
@FeignClient(value = "auth-server/perms")
public interface PermClientApi {

    @PostMapping("hasPerms")
    RspMsg hasPerms(@RequestBody PermsForm form);
}

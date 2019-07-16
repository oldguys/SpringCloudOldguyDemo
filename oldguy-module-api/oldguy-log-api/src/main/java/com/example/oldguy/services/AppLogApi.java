package com.example.oldguy.services;

import com.example.oldguy.model.dto.AppLogAddForm;
import com.example.oldguy.model.dto.RspMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName: AppLogApi
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 2:51
 **/
@FeignClient(value = "oldguy-log-server/logs")
public interface AppLogApi {

    @PostMapping("save")
    RspMsg save(@RequestBody AppLogAddForm form);
}

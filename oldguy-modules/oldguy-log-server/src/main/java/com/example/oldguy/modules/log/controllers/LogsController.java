package com.example.oldguy.modules.log.controllers;

import com.example.oldguy.model.dto.AppLogAddForm;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.services.AppLogApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: LogController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 11:20
 **/
@RestController
@RequestMapping("logs")
public class LogsController implements AppLogApi {

    @Override
    public RspMsg save(AppLogAddForm form) {
        return null;
    }
}

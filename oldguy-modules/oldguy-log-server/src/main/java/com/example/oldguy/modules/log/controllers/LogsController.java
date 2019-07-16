package com.example.oldguy.modules.log.controllers;

import com.example.oldguy.model.dto.AppLogAddForm;
import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.modules.commons.utils.FormValidateUtils;
import com.example.oldguy.modules.log.dao.entities.AppLog;
import com.example.oldguy.modules.log.dao.jpas.AppLogRepository;
import com.example.oldguy.services.AppLogApi;
import com.example.oldguy.services.UserSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName: LogController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 11:20
 **/
@RestController
@RequestMapping("logs")
public class LogsController implements AppLogApi {

    @Autowired
    private AppLogRepository appLogRepository;

    @Override
    public RspMsg save(AppLogAddForm form) {

        FormValidateUtils.validate(form);

        AppLog log = new AppLog();
        log.setData(form.getData());
        log.setType(form.getType());
        log.setMessage(form.getMessage());

        JwtInfo info = UserSessionUtils.getJwtInfo();
        log.setCreatorId(info.getUserId());
        log.setCreatorName(info.getUsername());
        log.setCreateTime(new Date());

        appLogRepository.save(log);

        return new RspMsg().data(form).defaultSuccessMessage();
    }
}

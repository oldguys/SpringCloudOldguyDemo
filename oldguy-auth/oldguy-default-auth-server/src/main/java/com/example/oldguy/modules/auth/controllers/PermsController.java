package com.example.oldguy.modules.auth.controllers;

import com.alibaba.fastjson.JSON;
import com.example.oldguy.model.constans.HttpStatus;
import com.example.oldguy.model.dto.PermsForm;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.modules.commons.utils.FormValidateUtils;
import com.example.oldguy.services.PermClientApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: PermsController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 9:13
 **/
@RestController
@RequestMapping("perms")
public class PermsController implements PermClientApi {

    @Override
    public RspMsg hasPerms(PermsForm form) {

        FormValidateUtils.validate(form);

        System.out.println(JSON.toJSON(form));

        return new RspMsg().code(HttpStatus.OK.value()).message("具有访问权限");
    }
}

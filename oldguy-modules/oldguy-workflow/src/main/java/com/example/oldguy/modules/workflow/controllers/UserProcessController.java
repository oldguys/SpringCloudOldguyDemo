package com.example.oldguy.modules.workflow.controllers;

import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.model.workflow.dto.UserProcessInfo;
import com.example.oldguy.services.wokflows.UserProcessApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: UserProcessController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/8/21 0021 上午 9:37
 **/
@RestController
@RequestMapping("UserProcess")
public class UserProcessController implements UserProcessApi {


    @Override
    public UserProcessInfo getInfo(String processInstanceId) {
        return null;
    }

    @Override
    public RspMsg<List<UserProcessInfo>> getCurrentProcessList() {
        return null;
    }
}

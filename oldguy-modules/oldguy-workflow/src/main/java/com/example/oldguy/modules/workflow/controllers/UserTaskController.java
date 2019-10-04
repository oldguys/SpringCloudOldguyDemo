package com.example.oldguy.modules.workflow.controllers;

import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.model.workflow.dto.UserTaskInfo;
import com.example.oldguy.services.wokflows.UserTaskApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: UserTaskController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/8/21 0021 上午 9:37
 **/
@RestController
@RequestMapping("UserTask")
public class UserTaskController implements UserTaskApi {


    @Override
    public RspMsg<UserTaskInfo> getTaskInfoByTaskId(String taskId) {
        return null;
    }

    @Override
    public RspMsg<List<UserTaskInfo>> getCurrentTaskList() {
        return null;
    }
}

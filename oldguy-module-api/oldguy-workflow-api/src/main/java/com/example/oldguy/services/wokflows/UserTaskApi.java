package com.example.oldguy.services.wokflows;

import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.model.workflow.dto.UserTaskInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @ClassName: UserTaskApi
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/8/21 0021 上午 9:39
 **/
@FeignClient("UserTask")
public interface UserTaskApi {

    /**
     *  获取任务详细信息
     * @param taskId
     * @return
     */
    @GetMapping("/{taskId}/info")
    RspMsg<UserTaskInfo> getTaskInfoByTaskId(@PathVariable("taskId") String taskId);

    /**
     * 获取当前用户任务列表
     *
     * @return
     */
    @GetMapping("current/list")
    RspMsg<List<UserTaskInfo>> getCurrentTaskList();
}

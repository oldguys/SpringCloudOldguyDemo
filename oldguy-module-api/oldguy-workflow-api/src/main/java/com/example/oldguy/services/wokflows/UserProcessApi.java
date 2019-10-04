package com.example.oldguy.services.wokflows;

import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.model.workflow.dto.UserProcessInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @ClassName: UserProcessApi
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/8/21 0021 上午 9:39
 **/
@FeignClient("UserProcess")
public interface UserProcessApi {

    /**
     *  根据流程详情ID获取流程信息
     * @param processInstanceId
     * @return
     */
    @GetMapping("{processInstanceId}/info")
    UserProcessInfo getInfo(@PathVariable("processInstanceId") String processInstanceId);

    /**
     * 获取当前用户的所有流程详细信息
     *
     * @return
     */
    @GetMapping("current/list")
    RspMsg<List<UserProcessInfo>> getCurrentProcessList();
}

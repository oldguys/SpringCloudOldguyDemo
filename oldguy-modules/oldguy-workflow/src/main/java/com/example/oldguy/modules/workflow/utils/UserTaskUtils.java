package com.example.oldguy.modules.workflow.utils;

import com.example.oldguy.model.workflow.dto.UserTaskInfo;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: UserTaskUtils
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/8/22 0022 上午 9:24
 **/
public class UserTaskUtils {

    public static List<UserTaskInfo> trainToInfoList(List<Task> sources, Map<String, ProcessInstance> processInstanceMap) {

        List<UserTaskInfo> records = new ArrayList<>();

        sources.forEach(obj -> {
            records.add(trainToInfo(obj, processInstanceMap.get(obj.getProcessInstanceId())));
        });

        return records;
    }

    public static UserTaskInfo trainToInfo(Task task, ProcessInstance processInstance) {
        UserTaskInfo info = new UserTaskInfo();

        info.setId(task.getId());
        info.setProcessInstanceId(task.getProcessInstanceId());
        info.setCreateTime(task.getCreateTime());
        info.setDuration(System.currentTimeMillis() - task.getCreateTime().getTime());

        info.setBusinessKey(processInstance.getBusinessKey());
        info.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        info.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
        info.setProcessDefinitionName(processInstance.getProcessDefinitionName());

        return info;
    }


}

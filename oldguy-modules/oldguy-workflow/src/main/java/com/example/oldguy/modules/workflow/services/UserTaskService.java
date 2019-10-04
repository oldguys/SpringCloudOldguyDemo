package com.example.oldguy.modules.workflow.services;

import com.example.oldguy.model.workflow.dto.UserTaskInfo;
import com.example.oldguy.modules.workflow.utils.UserTaskUtils;
import com.example.oldguy.services.UserSessionUtils;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName: UserTaskService
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/8/22 0022 上午 9:19
 **/
@Service
public class UserTaskService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;


    public List getCurrentTaskList() {
        String userId = UserSessionUtils.getJwtInfo().getUserId();

        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(userId).orderByTaskCreateTime().list();
        Set<String> processInstanceIdSet = new HashSet<>();
        list.forEach(obj -> {
            processInstanceIdSet.add(obj.getProcessInstanceId());
        });

        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(processInstanceIdSet).list();
        Map<String, ProcessInstance> processInstanceMap = new HashMap<>(processInstanceIdSet.size());
        processInstanceList.forEach(obj -> {
            processInstanceMap.put(obj.getId(), obj);
        });

        return UserTaskUtils.trainToInfoList(list,processInstanceMap);
    }
}

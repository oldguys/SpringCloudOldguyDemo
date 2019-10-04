package com.example.oldguy.modules.workflow.services.commands;

import com.example.oldguy.utils.Log4jUtils;
import com.example.oldguy.utils.SpringContextUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;


/**
 * @ClassName: AbstractCountersignCmd
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/5/13 0013 下午 11:43
 **/
public abstract class AbstractCountersignCmd {

    protected RuntimeService runtimeService;

    protected TaskService taskService;

    protected RepositoryService repositoryService;

    public AbstractCountersignCmd(){

        runtimeService = SpringContextUtils.getBean(RuntimeService.class);
        taskService = SpringContextUtils.getBean(TaskService.class);
        repositoryService = SpringContextUtils.getBean(RepositoryService.class);
    }

    protected UserTask getUserTask(TaskEntityImpl task) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        Process process = bpmnModel.getProcesses().get(0);

        UserTask userTask = (UserTask) process.getFlowElement(task.getTaskDefinitionKey());

        if (userTask.getLoopCharacteristics() == null) {
            // TODO
            Log4jUtils.getInstance(getClass()).error("task:[" + task.getId() + "] 不是会签节任务");
        }
        return userTask;
    }

}

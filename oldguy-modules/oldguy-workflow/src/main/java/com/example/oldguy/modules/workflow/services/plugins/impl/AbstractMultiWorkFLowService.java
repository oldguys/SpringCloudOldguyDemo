package com.example.oldguy.modules.workflow.services.plugins.impl;

import com.example.oldguy.modules.workflow.services.commands.AddMultiInstanceExecutionCmd;
import com.example.oldguy.modules.workflow.services.commands.DeleteMultiInstanceExecutionCmd;
import com.example.oldguy.modules.workflow.services.plugins.DefaultInstanceConvertToMultiInstance;
import com.example.oldguy.modules.workflow.services.plugins.FlowElementJump;
import com.example.oldguy.modules.workflow.services.plugins.MultiInstanceAlter;
import com.example.oldguy.utils.Log4jUtils;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.*;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AbstractMultiWorkFLowService
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/5/23 0023 下午 4:55
 **/
public abstract class AbstractMultiWorkFLowService implements DefaultInstanceConvertToMultiInstance, FlowElementJump, MultiInstanceAlter {

    @Autowired
    protected ProcessEngine processEngine;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    private ManagementService managementService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void multiInstanceAddInstances(String taskId, List<String> assigneeList, String assignee) {
        managementService.executeCommand(new AddMultiInstanceExecutionCmd(taskId, assigneeList, assignee));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void multiInstanceRemoveInstances(String taskId, List<String> assigneeList) {
        managementService.executeCommand(new DeleteMultiInstanceExecutionCmd(taskId, assigneeList));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void elementJumpToOthers(String taskId, Map<String, Object> data, String... useTasks) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        Process process = bpmnModel.getProcesses().get(0);

        /**
         *  获取需要 指向的流程节点，备用
         */
        List<FlowElement> targetUserTaskList = new ArrayList<>(useTasks.length);

        for (int i = 0; i < useTasks.length; i++) {

            String taskName = useTasks[i];
            FlowElement flowElement = process.getFlowElement(taskName);
            if (null != flowElement) {
                targetUserTaskList.add(flowElement);
            }
        }

        if (targetUserTaskList.isEmpty()) {
            throw new RuntimeException("没有找到跳转节点");
        }

        /**
         *  获取当前任务节点
         */
        FlowElement currentElement = process.getFlowElement(task.getTaskDefinitionKey());

        /**
         *  生成 需要指向的节点连线。
         */
        List<SequenceFlow> tempSequenceFlows = new ArrayList<>(targetUserTaskList.size());

        targetUserTaskList.forEach(obj -> {
            SequenceFlow flow = new SequenceFlow();

            flow.setSourceFlowElement(currentElement);
            flow.setSourceRef(currentElement.getId());
            flow.setTargetFlowElement(obj);
            flow.setTargetRef(obj.getId());

            tempSequenceFlows.add(flow);
        });


        /**
         *  修改流程指向，并完成任务
         */
        if (currentElement instanceof UserTask) {

            /**
             *  当前流程节点
             */
            UserTask current = (UserTask) currentElement;
            // 缓存原本的流向
            List<SequenceFlow> sourceFlows = current.getOutgoingFlows();
            // 使用临时流向
            current.setOutgoingFlows(tempSequenceFlows);

            /**
             *  完成任务
             */
            taskService.complete(taskId, data);

            /**
             * 完成任务后，替换为原本的流向
             *
             *  （虽然经过测试，就算不改变回来，也不会持久到数据库，下次调用不会改变）
             */
            current.setOutgoingFlows(sourceFlows);
        } else {
            Log4jUtils.getInstance(getClass()).error("不是任务节!");
        }

    }

    @Override
    public MultiInstanceLoopCharacteristics createMultiInstanceLoopCharacteristics(boolean isSequential) {
        return createMultiInstanceLoopCharacteristics(isSequential, DEFAULT_ASSIGNEE_LIST_EXP, ASSIGNEE_USER);
    }

    @Override
    public MultiInstanceLoopCharacteristics createMultiInstanceLoopCharacteristics(boolean isSequential, String assigneeListExp, String assignee) {

        MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
        multiInstanceLoopCharacteristics.setSequential(isSequential);
        multiInstanceLoopCharacteristics.setInputDataItem(assigneeListExp);
        multiInstanceLoopCharacteristics.setElementVariable(assignee);

        return multiInstanceLoopCharacteristics;
    }

    @Override
    public MultiInstanceActivityBehavior createMultiInstanceBehavior(UserTask userTask, boolean sequential) {
        return createMultiInstanceBehavior(userTask, sequential, DEFAULT_ASSIGNEE_LIST_EXP, ASSIGNEE_USER);
    }

    @Override
    public MultiInstanceActivityBehavior createMultiInstanceBehavior(UserTask userTask, boolean sequential, String assigneeListExp, String assignee) {


        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration();
        /**
         *  创建解释器
         */
        UserTaskActivityBehavior userTaskActivityBehavior = processEngineConfiguration.getActivityBehaviorFactory().createUserTaskActivityBehavior(userTask);

        MultiInstanceActivityBehavior behavior = null;

        if (sequential) {
            behavior = new SequentialMultiInstanceBehavior(userTask, userTaskActivityBehavior);
        } else {
            behavior = new ParallelMultiInstanceBehavior(userTask, userTaskActivityBehavior);
        }

        /**
         *   注入表达式 解释器
         */
        ExpressionManager expressionManager = processEngineConfiguration.getExpressionManager();

        /**
         * 设置表达式变量
         */
        behavior.setCollectionExpression(expressionManager.createExpression(assigneeListExp));
        behavior.setCollectionElementVariable(assignee);

        return behavior;
    }

}

package com.example.oldguy.modules.workflow.services.plugins;

import java.util.Map;

/**
 * @ClassName: FlowElementJump
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/5/23 0006 上午 10:57
 *
 * 动态流转节点 （1 ： N）
 * 非直线流程（任务开始时间） A > B = C > D ，在A任务完成时，同时开启 B ，C 任务。
 * <p>
 * 解决思路 ： 改动当前任务的流转线（新增），将流程节点流向改为多个节点。这样就可以
 * 动态扭转流程走向。
 * <p>
 * 注意：
 * 1. 改变 Bpmn Model.process 之后，再 完成任务。
 * 2. 一开始设置 .bpmn 文件的时候 在注入审批人 标志必须 可区分 如: assignee = ${assignee_b};assignee = ${assignee_c}
 * 以防止 出现审批人 出错。
 * 3. 最好在 完成任务之后，将流程流向更改回默认。（虽然已经确认 process 基于ThreadLocal ，但是防止意外）
 */
public interface FlowElementJump {

    /**
     *  流程节点跳转
     * @param taskId
     * @param data
     * @param useTasks
     */
    void elementJumpToOthers(String taskId, Map<String, Object> data, String... useTasks);
}

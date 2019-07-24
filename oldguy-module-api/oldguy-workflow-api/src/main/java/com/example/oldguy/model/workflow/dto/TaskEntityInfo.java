package com.example.oldguy.model.workflow.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @author huangrenhao
 * @date 2018/12/3
 */
@Data
public class TaskEntityInfo implements Comparable<TaskEntityInfo> {

    private String id;

    private String taskName;

    private String taskDefinitionKey;

    private String processInstanceId;

    private String processDefinitionId;

    /**
     *  流程定义Key
     */
    private String processDefinitionKey;

    private String processDefinitionName;

    private Set<String> assigners;

    private Set<String> assignerNames;

    private Date createTime;

    private String businessKey;

    private Long entityId;

    /**
     * 历时
     */
    private long duration;

    @Override
    public int compareTo(TaskEntityInfo o) {
        return (int) (o.createTime.getTime() - this.createTime.getTime());
    }
}

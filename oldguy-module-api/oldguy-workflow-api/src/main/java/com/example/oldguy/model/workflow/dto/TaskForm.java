package com.example.oldguy.model.workflow.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author huangrenhao
 * @date 2018/12/7
 */
@Data
public class TaskForm {

    /**
     * 任务ID
     */
    @NotBlank(message = "taskId 任务ID 不能为空!")
    private String taskId;

    /**
     * 批注
     */
    private String comment;

    /**
     * 流程标示
     */
    private String flowFlag;
}

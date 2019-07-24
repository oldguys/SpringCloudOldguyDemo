package com.example.oldguy.model.workflow.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author huangrenhao
 * @date 2018/12/5
 */
@Data
public class TaskComment {

    private String id;

    private String taskId;

    private String processInstanceId;

    private String userId;

    private String message;

    private Date time;
}

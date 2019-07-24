package com.example.oldguy.model.workflow.dao.entities;

import lombok.Data;

import java.util.Date;

/**
 * @author huangrenhao
 * @date 2018/12/26
 */
@Data
public class UserProcessInstance {

    private String processInstanceId;

    private String businessKey;

    private String creatorId;

    private Date startTime;

    private Date endTime;


    private String processDefinitionKey;

    private String processDefinitionName;

    private String taskName;

    private String assignees;

    /**
     *  流程状态
     */
    private String active;

}

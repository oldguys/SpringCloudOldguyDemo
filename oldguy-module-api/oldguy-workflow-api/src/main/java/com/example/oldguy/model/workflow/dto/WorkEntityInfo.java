package com.example.oldguy.model.workflow.dto;

import lombok.Data;

import java.util.List;

/**
 * 任务实体
 *
 * @author huangrenhao
 * @date 2018/12/5
 */
@Data
public class WorkEntityInfo {

    /**
     * 任务信息
     */
    private UserTaskInfo taskEntityInfo;

    /**
     * 任务批注
     */
    private List<TaskComment> taskComments;

    /**
     * 任务按钮
     */
    private List<WorkBtn> workBtnList;

    /**
     * 数据对象
     */
    private Object target;

}

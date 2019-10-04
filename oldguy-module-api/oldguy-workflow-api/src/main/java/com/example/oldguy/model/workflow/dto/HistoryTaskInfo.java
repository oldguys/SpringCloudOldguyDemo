package com.example.oldguy.model.workflow.dto;


import com.example.oldguy.model.workflow.dao.entities.HistoryTask;
import lombok.Data;

/**
 * @author huangrenhao
 * @date 2019/1/22
 */
@Data
public class HistoryTaskInfo extends HistoryTask {

    private String processDefineName;

    private String lastCommit;

}


package com.example.oldguy.model.workflow.dto;


import com.example.oldguy.model.workflow.dao.entities.HistoryTask;

/**
 * @author huangrenhao
 * @date 2019/1/22
 */
public class HistoryTaskInfo extends HistoryTask {

    private String processDefineName;

    private String lastCommit;

    public String getLastCommit() {
        return lastCommit;
    }

    public void setLastCommit(String lastCommit) {
        this.lastCommit = lastCommit;
    }

    public String getProcessDefineName() {
        return processDefineName;
    }

    public void setProcessDefineName(String processDefineName) {
        this.processDefineName = processDefineName;
    }
}


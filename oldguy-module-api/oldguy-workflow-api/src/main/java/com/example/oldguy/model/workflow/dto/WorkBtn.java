package com.example.oldguy.model.workflow.dto;

import lombok.Data;

/**
 * @author huangrenhao
 * @date 2018/12/6
 */
@Data
public class WorkBtn {

    /**
     *  按钮名称
     */
    private String name;

    /**
     *  路径
     */
    private String url;

    /**
     *  提交标示
     */
    private Object flowFlag;

    public WorkBtn(String name, String url, Object flowFlag) {
        this.name = name;
        this.url = url;
        this.flowFlag = flowFlag;
    }

}

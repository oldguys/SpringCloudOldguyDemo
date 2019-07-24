package com.example.oldguy.modules.workflow.services.plugins;

import java.util.Map;

/**
 * @ClassName: MultiInstanceAlter
 * @Author: ren
 * @Description: 特定业务完成类
 * @CreateTIme: 2019/5/24 0024 下午 2:17
 **/
public interface MultiWorkflow {

    /**
     *
     * @param taskId
     * @param data
     */
    void completeTask(String taskId, Map<String, Object> data);
}

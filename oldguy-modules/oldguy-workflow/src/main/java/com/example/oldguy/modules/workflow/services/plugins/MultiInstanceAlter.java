package com.example.oldguy.modules.workflow.services.plugins;

import java.util.List;

/**
 * @ClassName: MultiInstanceAlter
 * @Author: ren
 * @Description: 会签 加减签
 * @CreateTIme: 2019/5/24 0024 下午 2:17
 **/
public interface MultiInstanceAlter {

    /**
     *  会签加签
     * @param taskId
     * @param assigneeList
     */
    void multiInstanceAddInstances(String taskId, List<String> assigneeList, String assignee);

    /**
     *  会签减签
     * @param taskId
     * @param assigneeList
     */
    void multiInstanceRemoveInstances(String taskId, List<String> assigneeList);
}

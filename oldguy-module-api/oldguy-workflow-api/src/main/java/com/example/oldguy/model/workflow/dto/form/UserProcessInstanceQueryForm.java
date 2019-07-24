package com.example.oldguy.model.workflow.dto.form;


import com.example.oldguy.model.dto.form.AbstractPageQueryForm;

/**
 * @author huangrenhao
 * @date 2018/12/26
 */
public class UserProcessInstanceQueryForm extends AbstractPageQueryForm {

    /**
     *  用户ID
     */
    private String userId;

    /**
     *  1:当前用户
     *  0:包含所有用户
     */
    private Integer currentUser = 0;

    public Integer getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Integer currentUser) {
        this.currentUser = currentUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

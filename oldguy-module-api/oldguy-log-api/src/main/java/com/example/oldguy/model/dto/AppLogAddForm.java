package com.example.oldguy.model.dto;

import com.example.oldguy.model.dao.entities.AppLog;
import com.example.oldguy.model.dto.form.AbstractForm;

/**
 * @ClassName: AppLogAddForm
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 2:54
 **/
public class AppLogAddForm extends AbstractForm<AppLog> {
    @Override
    public AppLog trainToEntity() {
        return defaultTrainToEntity(AppLog.class);
    }
}

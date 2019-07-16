package com.example.oldguy.model.dto.form;


import com.example.oldguy.utils.ReflectUtils;

/**
 * @author huangrenhao
 * @date 2018/11/22
 */
public abstract class AbstractForm<T> implements Form {

    protected <T> T defaultTrainToEntity(Class<T> clazz) {

        try {
            return ReflectUtils.updateEntityFormToEntity(this, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

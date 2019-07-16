package com.example.oldguy.services;

import java.io.Serializable;

/**
 * @ClassName: BaseControllerTemplate
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/5 0005 下午 3:45
 **/
public interface BaseControllerTemplate<T> {

    /**
     * 获取单个实体 模板
     *
     * @param id
     * @return
     */
    T selectOneTemplate(Serializable id);

    /**
     * 更新模板
     *
     * @param entity
     * @return
     */
    int updateTemplate(T entity);
}

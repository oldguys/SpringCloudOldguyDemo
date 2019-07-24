package com.example.oldguy.model.workflow.dao.entities;

import com.example.oldguy.model.annotation.Entity;
import com.example.oldguy.model.dao.entities.BaseEntity;
import lombok.Data;

/**
 * @ClassName: TransformEntityInfo
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/5/23 0023 下午 2:58
 **/
@Entity
@Data
public class TransformEntityInfo extends BaseEntity {

    private String processInstanceId;

    private String taskDefineKey;

    /**
     *  是否串行：
     *  串行：true
     *  并行：false
     */
    private Boolean sequential;

}

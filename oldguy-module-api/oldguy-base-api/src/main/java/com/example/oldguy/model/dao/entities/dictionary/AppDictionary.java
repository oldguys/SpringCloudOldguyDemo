package com.example.oldguy.model.dao.entities.dictionary;


import com.example.oldguy.model.annotation.Entity;
import com.example.oldguy.model.dao.entities.CommonBaseEntity;
import lombok.Data;


/**
 * @ClassName: AppDictionary
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0017 上午 9:07
 **/
@Entity(pre = "sys_", comment = "字典描述")
@Data
public class AppDictionary extends CommonBaseEntity {

    /**
     * 服务编码
     */
    private String serviceCode;

    /**
     * 字典编码
     */
    private String dicCode;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String fieldValue;

    /**
     * 描述
     */
    private String description;

}

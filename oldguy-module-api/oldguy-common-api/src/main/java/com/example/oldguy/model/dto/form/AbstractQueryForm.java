package com.example.oldguy.model.dto.form;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huangrenhao
 * @date 2018/11/26
 */
@Data
public abstract class AbstractQueryForm {

    /**
     * 模糊搜索字段
     */
    @ApiModelProperty("模糊搜索字段")
    private String queryText;

    /**
     * 排序：
     * 1 - > id 倒序
     * 0 - > id 正序
     */
    @ApiModelProperty("排序：1 - > id 倒序;0 - > id 正序")
    private Integer sort;

    /**
     * 状态：
     * 1 - > 正常
     * 0 - > 禁用
     */
    @ApiModelProperty("状态： 1 - > 正常;0 - > 禁用")
    private Integer isDeleted;

}

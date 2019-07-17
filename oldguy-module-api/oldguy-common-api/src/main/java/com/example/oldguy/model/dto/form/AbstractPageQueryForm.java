package com.example.oldguy.model.dto.form;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oldguy.model.constans.PageConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author huangrenhao
 * @date 2019/1/9
 */
@Data
public class AbstractPageQueryForm<T> extends AbstractQueryForm {

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Integer current;

    /**
     * 单页记录数
     */
    @ApiModelProperty("单页记录数")
    private Integer size;

    /**
     * 创建时间 > 开始时间
     */
    @DateTimeFormat
    @ApiModelProperty("创建时间 > 开始时间")
    private Date startTime;

    /**
     * 创建时间 < 结束时间
     */
    @DateTimeFormat
    @ApiModelProperty("创建时间 < 结束时间")
    private Date endTime;

    public Page<T> trainToPage() {

        current = current == null ? current = 0 : current;
        size = size == null ? size = PageConstants.DEFAULT_PAGE_SIZE : size;

        return new Page<>(current, size);
    }

}

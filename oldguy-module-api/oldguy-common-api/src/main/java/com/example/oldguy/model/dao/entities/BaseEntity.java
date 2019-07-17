package com.example.oldguy.model.dao.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.example.oldguy.model.annotation.Comment;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @ClassName: BaseEntity
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/27 0027 下午 7:40
 **/
@Data
public class BaseEntity {

    @TableId
    private String id;

    @Comment("创建人ID")
    private Date createTime;

    /**
     *  删除状态：
     *  1 ： 已删除
     *  0 ：未删除
     */
    @Column(nullable = false)
    @Comment("删除状态[1:已删除；0:未删除]")
    private Integer isDeleted;
}

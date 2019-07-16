package com.example.oldguy.model.dao.entities;

import com.baomidou.mybatisplus.annotation.TableId;
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
//@MappedSuperclass
public class BaseEntity {

//    @Id
    @TableId
//    @GeneratedValue
    private String id;

    private Date createTime;

    /**
     *  删除状态：
     *  1 ： 已删除
     *  0 ：未删除
     */
    @Column(nullable = false)
    private Integer isDeleted;
}

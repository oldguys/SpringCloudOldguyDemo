package com.example.oldguy.model.dao.entities;

import com.example.oldguy.model.annotation.Comment;
import com.example.oldguy.model.annotation.Entity;
import lombok.Data;

import javax.persistence.Column;

/**
 * @ClassName: UserGroup
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/22 0022 下午 3:40
 **/
@Data
@Entity(pre = "sys_", comment = "用户组信息表")
public class UserGroup extends CommonBaseEntity {

    /**
     * 名称
     */
    @Comment("用户组名称")
    private String groupName;

    /**
     * 描述
     */
    @Comment("描述")
    private String description;

    /**
     * 标识
     */
    @Comment("标识")
    @Column(nullable = false, unique = true)
    private String groupFlag;

    /**
     * 服务ID
     */
    @Comment("服务ID")
    private String serviceId;
}

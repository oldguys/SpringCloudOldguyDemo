package com.example.oldguy.model.dao.entities;

import com.example.oldguy.model.annotation.Comment;
import com.example.oldguy.model.annotation.Entity;
import lombok.Data;

import javax.persistence.Column;

/**
 * @ClassName: Role
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/22 0022 下午 3:41
 **/
@Data
@Entity(pre = "sys_", comment = "角色信息表")
public class Role extends CommonBaseEntity {

    /**
     *  角色名
     */
    @Comment("角色名")
    private String roleName;

    /**
     *  描述
     */
    @Comment("描述")
    private String description;

    /**
     *  角色标识
     */
    @Comment("角色标识")
    @Column(nullable = false, unique = true)
    private String roleFlag;

    /**
     *  服务ID
     */
    @Comment("服务ID")
    private String serviceId;
}

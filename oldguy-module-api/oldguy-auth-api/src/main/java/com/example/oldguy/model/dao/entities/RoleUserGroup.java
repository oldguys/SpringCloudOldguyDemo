package com.example.oldguy.model.dao.entities;

import com.example.oldguy.model.annotation.AssociateEntity;
import lombok.Data;

/**
 * @ClassName: RoleUserGroup
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/22 0022 下午 3:57
 **/
@Data
@AssociateEntity(pre = "sys_", comment = "角色-用户组 多对多")
public class RoleUserGroup {

    private String id;

    /**
     * 服务编号
     */
    private String serviceId;

    /**
     * 角色标识
     */
    private String roleFlag;

    /**
     * 用户组标识
     */
    private String groupFlag;
}

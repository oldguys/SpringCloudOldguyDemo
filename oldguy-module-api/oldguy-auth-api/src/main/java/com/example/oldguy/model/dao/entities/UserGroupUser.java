package com.example.oldguy.model.dao.entities;

import com.example.oldguy.model.annotation.AssociateEntity;
import lombok.Data;

/**
 * @ClassName: UserGroupUser
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/22 0022 下午 3:58
 **/
@Data
@AssociateEntity(pre = "sys_", comment = "用户组-用户 多对多")
public class UserGroupUser {


    private String id;

    /**
     * 服务编号
     */
    private String serviceId;

    /**
     * 用户组标识
     */
    private String groupFlag;

    /**
     * 用户ID
     */
    private String userId;
}

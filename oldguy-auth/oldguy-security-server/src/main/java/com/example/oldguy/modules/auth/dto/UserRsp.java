package com.example.oldguy.modules.auth.dto;

import com.example.oldguy.model.workflow.dao.entities.UserEntity;
import lombok.Data;

/**
 * @ClassName: UserRsp
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/30 0030 下午 6:59
 **/
@Data
public class UserRsp {

    private UserEntity user;

    private String token;
}

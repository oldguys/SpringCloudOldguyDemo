package com.example.oldguy.modules.auth.controllers;

import com.example.oldguy.model.dao.entities.UserGroup;
import com.example.oldguy.modules.commons.controllers.BaseEntityController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserGroupController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/22 0022 下午 3:48
 **/
@Api(tags = "用户组操作")
@RestController
@RequestMapping("userGroup")
public class UserGroupController extends BaseEntityController<UserGroup> {
}

package com.example.oldguy.modules.auth.controllers;

import com.example.oldguy.model.dao.entities.Role;
import com.example.oldguy.modules.commons.controllers.BaseEntityController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: RoleController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/22 0022 下午 3:48
 **/
@Api(tags = "角色操作")
@RestController
@RequestMapping("role")
public class RoleController extends BaseEntityController<Role> {
}

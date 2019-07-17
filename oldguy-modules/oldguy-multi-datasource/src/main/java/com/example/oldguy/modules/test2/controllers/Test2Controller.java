package com.example.oldguy.modules.test2.controllers;

import com.example.oldguy.modules.commons.controllers.BaseEntityController;
import com.example.oldguy.modules.test2.dao.entities.Test2Entity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: Test1Controller
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0017 下午 2:39
 **/
@RestController
@RequestMapping("test2")
public class Test2Controller extends BaseEntityController<Test2Entity> {
}

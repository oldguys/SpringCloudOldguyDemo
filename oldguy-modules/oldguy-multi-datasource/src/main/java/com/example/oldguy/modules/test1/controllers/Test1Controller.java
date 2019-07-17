package com.example.oldguy.modules.test1.controllers;

import com.example.oldguy.modules.commons.controllers.BaseEntityController;
import com.example.oldguy.modules.test1.dao.entities.Test1Entity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: Test1Controller
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0017 下午 2:39
 **/
@RestController
@RequestMapping("test1")
public class Test1Controller extends BaseEntityController<Test1Entity> {
}

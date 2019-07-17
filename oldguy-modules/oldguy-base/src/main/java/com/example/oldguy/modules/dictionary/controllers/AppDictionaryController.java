package com.example.oldguy.modules.dictionary.controllers;

import com.example.oldguy.model.dao.entities.dictionary.AppDictionary;
import com.example.oldguy.modules.commons.controllers.BaseEntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: AppDictionaryController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0017 上午 9:50
 **/
@RestController
@RequestMapping("appDictionary")
public class AppDictionaryController extends BaseEntityController<AppDictionary> {
}

package com.example.oldguy.modules.test1.dao.entities;

import com.example.oldguy.model.annotation.Entity;
import com.example.oldguy.model.dao.entities.CommonBaseEntity;
import lombok.Data;

/**
 * @ClassName: Test1Entity
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0017 下午 1:59
 **/
@Data
@Entity
public class Test1Entity extends CommonBaseEntity {

    private String test1;
}

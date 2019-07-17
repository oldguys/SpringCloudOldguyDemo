package com.example.oldguy.modules.commons.controllers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.model.dto.form.AbstractPageQueryForm;
import com.example.oldguy.modules.commons.dao.jpas.BaseEntityMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: BaseEntityController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0017 上午 9:43
 **/
public abstract class BaseEntityController<T> extends BaseController<T, String> {

    @Autowired
    protected BaseEntityMapper<T> baseEntityMapper;

    @Override
    public T selectOneTemplate(Serializable id) {
        return baseEntityMapper.findOne(id);
    }


    /**
     * 默认-更新状态
     * @param id
     * @param isDeleted
     * @return
     */
    @PutMapping("{id}/{isDeleted}")
    @ApiOperation(value = "默认-编辑状态", position = 102)
    public RspMsg<T> updateStatus(@PathVariable("id") String id,
                                  @PathVariable("isDeleted") Integer isDeleted) {

        return defaultRspMsgByInt(baseEntityMapper.updateDeleted(id, isDeleted));
    }

    /**
     *  默认-获取分页列表
     * @param form
     * @return
     */
    @GetMapping("page")
    @ApiOperation(value = "默认-分页", position = 100)
    public RspMsg<T> page(AbstractPageQueryForm form){

        Page<T> page = form.trainToPage();
        List<T> records = baseEntityMapper.findByPage(page, form);
        page.setRecords(records);

        return new RspMsg<T>().page(page).defaultSuccessMessage();
    }
}

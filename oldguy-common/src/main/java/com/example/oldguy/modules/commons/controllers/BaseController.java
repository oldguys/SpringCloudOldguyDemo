package com.example.oldguy.modules.commons.controllers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oldguy.model.constans.RspMsgConstants;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.services.BaseControllerTemplate;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * @ClassName: BaseController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0017 上午 9:14
 **/
public abstract class BaseController<T, S extends Serializable> implements BaseControllerTemplate<T> {

    @Autowired
    protected BaseMapper<T> baseMapper;

    @Override
    public T selectOneTemplate(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    public int updateTemplate(T entity) {
        return baseMapper.updateById(entity);
    }

    @ApiOperation(value = "默认-获取单个信息", position = 101)
    @GetMapping("{id}")
    public RspMsg<T> findOne(@PathVariable("id") Serializable id) {

        T target = selectOneTemplate(id);

        if (null == target) {
            return new RspMsg().code(HttpStatus.BAD_REQUEST.value()).message(RspMsgConstants.getNotFoundMessage(id));
        }
        return new RspMsg<T>().data(target).defaultSuccessMessage();
    }

    @ApiOperation(value = "默认-更新", position = 103)
    @PutMapping("update")
    public RspMsg<T> updateOne(@RequestBody T entity) {
        return defaultRspMsgByInt(updateTemplate(entity));
    }

    /**
     * 根据更新结果返回
     *
     * @param result
     * @return
     */
    public RspMsg defaultRspMsgByInt(int result) {
        return result > 0 ? new RspMsg().defaultSuccessMessage() : new RspMsg().defaultErrorMessage();
    }
}

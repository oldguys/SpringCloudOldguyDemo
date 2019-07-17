package com.example.oldguy.modules.commons.dao.jpas;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oldguy.model.dto.form.AbstractPageQueryForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体映射基类，用于映射BaseEntity的子类
 *
 * @param <T>
 * @author huangrenhao
 * @version V1.0
 * @ClassName: BaseEntityMapper
 * @Description: TODO
 * @date 2017年12月4日 上午10:44:55
 */
public interface BaseEntityMapper<T> extends BaseMapper<T, String>, com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 获取 List<T> 列表
     * isDeleted :
     * 1 - 有效
     * 0 - 无效
     * null -> 所有
     *
     * @param isDeleted
     * @return
     */
    List<T> findAll(@Param("isDeleted") Integer isDeleted);

    /**
     * 修改 T 状态
     *
     * @param id
     * @param isDeleted 是否删除
     *      1 ： 已删除
     *      0 ： 未删除
     * @return
     */
    int updateDeleted(@Param("id") String id, @Param("isDeleted") Integer isDeleted);

    /**
     *  获取分页数据
     * @param page
     * @param form
     * @return
     */
    List<T> findByPage(Page<T> page, @Param("form") AbstractPageQueryForm<T> form);
}

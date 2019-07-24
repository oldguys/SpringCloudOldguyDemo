package com.example.oldguy.modules.workflow.dao.jpas;

import com.example.oldguy.model.workflow.dao.entities.HistoryTask;
import com.example.oldguy.modules.commons.dao.jpas.BaseEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huangrenhao
 * @date 2019/1/21
 */
@Repository
public interface HistoryTaskMapper extends BaseEntityMapper {

    /**
     *  通过用户ID
     * @param userId
     * @return
     */
    List<HistoryTask> findByUserId(String userId);
}

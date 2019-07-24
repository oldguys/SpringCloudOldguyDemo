package com.example.oldguy.modules.workflow.dao.jpas;

import com.example.oldguy.model.workflow.dao.entities.ProcessTaskConfig;
import com.example.oldguy.modules.commons.dao.jpas.BaseEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date: 2019/1/26 0026
 * @Author: ren
 * @Description:
 */
@Repository
public interface ProcessTaskConfigMapper extends BaseEntityMapper<ProcessTaskConfig> {


    /**
     *  获取流程定义信息
     * @param processDefinitionId
     * @return
     */
    List<ProcessTaskConfig> findByProcessDefinitionId(String processDefinitionId);

    /**
     *  获取流程类别最新流程定义信息
     * @param processDefinitionKey
     * @return
     */
    List<ProcessTaskConfig> findByProcessDefinitionKey(String processDefinitionKey);
}

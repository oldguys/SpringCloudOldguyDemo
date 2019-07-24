package com.example.oldguy.modules.workflow.dao.jpas;

import com.example.oldguy.model.workflow.dao.entities.TransformEntityInfo;
import com.example.oldguy.modules.commons.dao.jpas.BaseEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface TransformEntityInfoMapper extends BaseEntityMapper<TransformEntityInfo> {

    TransformEntityInfo findByProcessInstance(String processInstanceId);

}

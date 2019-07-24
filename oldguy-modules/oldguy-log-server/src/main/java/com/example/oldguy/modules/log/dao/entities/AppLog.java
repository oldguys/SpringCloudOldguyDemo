package com.example.oldguy.modules.log.dao.entities;


import com.example.oldguy.model.dao.entities.BaseEntity;
import com.example.oldguy.services.CreatorSetterImpl;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName: AppLog
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 2:35
 **/
@Data
@Document(collection = "app-logs")
public class AppLog extends BaseEntity implements CreatorSetterImpl {

    /**
     *  创建人ID
     */
    private String creatorId;

    /**
     *  创建人姓名
     */
    private String creatorName;

    /**
     *  日志类型
     */
    private String type;

    /**
     *  日志内容
     */
    private String message;

    /**
     *  数据
     */
    private String data;
}

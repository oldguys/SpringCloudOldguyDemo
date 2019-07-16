package com.example.oldguy.model.dao.entities;


import com.example.oldguy.services.CreatorSetterImpl;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: AppLog
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 2:35
 **/
@Data
public class AppLog extends BaseEntity implements CreatorSetterImpl {

    /**
     *  创建人ID
     */
    private String creatorId;

    /**
     *  创建人姓名
     */
    private String creatorName;
}

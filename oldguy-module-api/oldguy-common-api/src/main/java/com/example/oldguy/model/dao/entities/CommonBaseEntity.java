package com.example.oldguy.model.dao.entities;

import com.example.oldguy.model.annotation.Comment;
import com.example.oldguy.services.CreatorSetterImpl;
import com.example.oldguy.services.UpdaterSetterImpl;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @ClassName: CommonBaseEntity
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/27 0027 下午 7:49
 **/
@Data
@MappedSuperclass
public class CommonBaseEntity extends BaseEntity implements CreatorSetterImpl, UpdaterSetterImpl {

    /**
     *  创建人ID
     */
    @Comment("创建人ID")
    private String creatorId;

    /**
     *  创建人姓名
     */
    @Comment("创建人姓名")
    private String creatorName;

    /**
     *  最后更新人ID
     */
    @Comment("最后更新人ID")
    private String updaterId;

    /**
     *  最后更新人姓名
     */
    @Comment("最后更新人姓名")
    private String updaterName;

    /**
     *  最后更新时间
     */
    @Comment("最后更新时间")
    private Date updateTime;

}

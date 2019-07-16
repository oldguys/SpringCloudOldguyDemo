package com.example.oldguy.configs.aop;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.oldguy.model.dao.entities.BaseEntity;
import com.example.oldguy.utils.Log4jUtils;
import com.example.oldguy.services.CreatorSetterImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Collection;
import java.util.Date;

/**
 * @ClassName: BaseEntityMapperAspect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/19 0019 上午 10:34
 **/
//@Aspect
//@Component
public class BaseEntityMapperAspect {

    @Pointcut(value = "execution(* com.example.oldguy.modules.commons.dao.jpas.BaseMapper.save(..))")
    public void savePointCut() {
    }

    @Pointcut(value = "execution(* com.example.oldguy.modules.commons.dao.jpas.BaseMapper.saveBatch(..))")
    public void saveBatchPointCut() {
    }

    @Before(value = "savePointCut()")
    public void beforeSave(JoinPoint joinPoint) {
        Log4jUtils.getInstance(getClass()).debug("持久化 补全 id AOP");
        Object[] args = joinPoint.getArgs();

        if (args.length > 0) {
            Object temp = args[0];
            if (temp instanceof BaseEntity) {

                Log4jUtils.getInstance(getClass()).debug("生成uuid");

                BaseEntity entity = (BaseEntity) temp;
                entity.setIsDeleted(0);
                entity.setId(IdWorker.getIdStr());
                entity.setCreateTime(new Date());
            }
            if(temp instanceof CreatorSetterImpl){
                CreatorSetterImpl setter = (CreatorSetterImpl) temp;
                // TODO
                setter.setCreatorId("创建人ID");
                setter.setCreatorName("创建人姓名");
            }
        }
    }

    @Before(value = "saveBatchPointCut()")
    public void beforeSaveBatch(JoinPoint joinPoint) {
        Log4jUtils.getInstance(getClass()).debug("持久化 补全 id AOP");
        Object[] args = joinPoint.getArgs();

        if (args.length > 0) {
            Object temp = args[0];
            if (temp instanceof Collection) {


                Collection<BaseEntity> collection = (Collection) temp;

                if (collection.isEmpty()) {
                    throw new RuntimeException("批量插入不能为 空集合 ");
                }

                Log4jUtils.getInstance(getClass()).debug("生成uuid size: " + collection.size());

                collection.forEach(obj -> {
                    obj.setCreateTime(new Date());
                    obj.setIsDeleted(0);
                    obj.setId(IdWorker.getIdStr());

                    if(obj instanceof CreatorSetterImpl){
                        CreatorSetterImpl setter = (CreatorSetterImpl) obj;
                        // TODO
                        setter.setCreatorId("创建人ID");
                        setter.setCreatorName("创建人姓名");
                    }

                });
            }
        }
    }

}

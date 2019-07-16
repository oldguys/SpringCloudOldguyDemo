package com.example.oldguy.modules.commons.interceptors;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.oldguy.model.dao.entities.BaseEntity;
import com.example.oldguy.model.dao.entities.UserEntity;
import com.example.oldguy.services.UserSessionUtils;
import com.example.oldguy.utils.Log4jUtils;
import com.example.oldguy.services.CreatorSetterImpl;
import com.example.oldguy.services.UpdaterSetterImpl;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.util.*;

/**
 * @ClassName: BaseEntityMapperInterceptor
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/8 0008 下午 5:26
 **/
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class BaseEntityMapperInterceptor implements Interceptor, WapperInterface {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        //获取代理对象，由于注解的反射，返回的为	确定类型 StatementHandler 的实例对象
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        //通过包装类，把statementHandler包装成一个可以调用反射的代理对象
        MetaObject metaObject = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);

        //事实上拦截到的时RoutingStatementHandler，需要调用其中的 delegate 属性，才可以调用到  mappedStatement，然后通过反射调用获取到mappedStatement对象
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        DefaultParameterHandler parameterHandler = (DefaultParameterHandler) metaObject.getValue("delegate.parameterHandler");

        //获取查询映射的名称
        String id = mappedStatement.getId();
        String methodName = id.substring(id.lastIndexOf(".") + 1);

        Log4jUtils.getInstance(getClass()).debug("进行通用拦截：" + methodName);
        Object parameter = parameterHandler.getParameterObject();

        if (methodName.matches("save.*")) {
            // 配置通用变量
            initSaveMethod(parameter);
            if (parameter instanceof HashMap) {

                HashMap map = (HashMap) parameter;

                map.forEach((k, v) -> {
                    if (v instanceof Collection) {
                        Collection collection = (Collection) v;

                        collection.forEach(obj -> {
                            // 配置通用变量
                            initSaveMethod(obj);
                        });
                    }
                });

            }
        } else if (methodName.matches("update.*")) {

            initUpdateMethod(parameter);
            if (parameter instanceof HashMap) {
                HashMap map = (HashMap) parameter;

                map.forEach((k, v) -> {
                    if (v instanceof Collection) {
                        Collection collection = (Collection) v;

                        collection.forEach(obj -> {
                            // 配置通用变量
                            initUpdateMethod(obj);
                        });
                    }
                });
            }


        }

        return invocation.proceed();
    }

    /**
     * 配置通用更新方法
     *
     * @param parameter
     */
    private void initUpdateMethod(Object parameter) {
        if (parameter instanceof UpdaterSetterImpl) {
            UserEntity user = UserSessionUtils.getUserEntity();
            if (null != user) {
                UpdaterSetterImpl setter = (UpdaterSetterImpl) parameter;
                setter.setUpdaterId(user.getUserId());
                setter.setUpdaterName(user.getUsername());
                setter.setUpdateTime(new Date());
            }else{
                Log4jUtils.getInstance(getClass()).warn("当前session不存在用户信息!");
            }
        }
    }

    /**
     * 配置通用新增方法
     *
     * @param parameter
     */
    private void initSaveMethod(Object parameter) {
        if (parameter instanceof BaseEntity) {
            Log4jUtils.getInstance(getClass()).debug("配置通用持久化变量");

            BaseEntity baseEntity = (BaseEntity) parameter;
            baseEntity.setId(IdWorker.getIdStr());
            baseEntity.setIsDeleted(0);
            baseEntity.setCreateTime(new Date());
        }

        if (parameter instanceof CreatorSetterImpl) {
            Log4jUtils.getInstance(getClass()).debug("配置创建人信息");
            UserEntity user = UserSessionUtils.getUserEntity();
            if (null != user) {
                CreatorSetterImpl setter = (CreatorSetterImpl) parameter;
                setter.setCreatorId(user.getUserId());
                setter.setCreatorName(user.getUsername());
            }else{
                Log4jUtils.getInstance(getClass()).warn("当前session不存在用户信息!");
            }
        }
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

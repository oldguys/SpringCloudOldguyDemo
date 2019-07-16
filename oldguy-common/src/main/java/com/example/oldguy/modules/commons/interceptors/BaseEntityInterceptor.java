package com.example.oldguy.modules.commons.interceptors;

import com.baomidou.mybatisplus.core.MybatisDefaultParameterHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;


import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @ClassName: BaseEntityInterceptor
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/6 0006 下午 6:56
 **/
@Deprecated
//@Intercepts({@Signature(type= StatementHandler.class,method="prepare",args={Connection.class,Integer.class}) })
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})})
public class BaseEntityInterceptor implements Interceptor, WapperInterface {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {


        System.out.println("test。。。。。。。。。。。。。。");

        MybatisDefaultParameterHandler parameterHandler = (MybatisDefaultParameterHandler) invocation.getTarget();

        //通过包装类，把statementHandler包装成一个可以调用反射的代理对象
        MetaObject metaObject = MetaObject.forObject(parameterHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);

        //事实上拦截到的时RoutingStatementHandler，需要调用其中的 delegate 属性，才可以调用到  mappedStatement，然后通过反射调用获取到mappedStatement对象
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");

//        //获取查询映射的名称
//        String id = mappedStatement.getId();
//        String methodName = id.substring(id.lastIndexOf(".") + 1);
//
//        System.out.println("methodName:" + methodName);
//
//        /**
//         *
//         */
//        UserEntity entity = UserSessionUtils.getUserEntity();
//
//        if (methodName.matches("save*")) {
//            ParameterMap parameterMap = mappedStatement.getParameterMap();
//            System.out.println("测试 save接口.............");
//
//            List<ParameterMapping> parameterMappings = parameterMap.getParameterMappings();
//
//
//        } else if (methodName.matches("update*")) {
//            System.out.println("测试 update接口.............");
//        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

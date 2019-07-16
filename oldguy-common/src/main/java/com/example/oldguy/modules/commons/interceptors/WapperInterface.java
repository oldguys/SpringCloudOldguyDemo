package com.example.oldguy.modules.commons.interceptors;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

/**
 * @ClassName: WapperInterface
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/6 0006 下午 7:38
 **/
public interface WapperInterface {

    /**
     * 默认ObjectFactory
     */
    ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();

    /**
     * 默认ObjectWrapperFactory
     */
    ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

    /**
     * 默认ReflectorFactory
     */
    ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

}

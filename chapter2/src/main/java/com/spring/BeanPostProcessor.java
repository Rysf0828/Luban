package com.spring;

/**
 * @author Hyy
 * @title: LubanBeanPostProcessor
 * @description: TODO
 * @date 2020/11/30
 */

public interface BeanPostProcessor {

    public void postProcessBeforeInitialization(String beanName,Object o);
    public void postProcessAfterInitialization(String beanName,Object o);
}

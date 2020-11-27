package com.spring;

/**
 * @author Hyy
 * @title: BeanPostProcess
 * @description: TODO
 * @date 2020/11/27
 */

public interface BeanPostProcessor {

    void postProcessBeforeInitialization(String beanName,Object o);
    void postProcessAfterInitialization(String beanName,Object o);
}

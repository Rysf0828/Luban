package com.luban;

import com.spring.BeanPostProcessor;
import com.spring.Component;

/**
 * @author Hyy
 * @title: LubanBeanPostProcessor
 * @description: TODO
 * @date 2020/11/30
 */

@Component
public class LubanBeanPostProcessor implements BeanPostProcessor {
    public void postProcessBeforeInitialization(String beanName, Object o) {

    }

    public void postProcessAfterInitialization(String beanName, Object o) {
        System.out.println("post process");
    }
}

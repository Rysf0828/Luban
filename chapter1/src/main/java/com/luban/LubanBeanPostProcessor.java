package com.luban;

import com.spring.BeanPostProcessor;
import com.spring.Component;

/**
 * @author Hyy
 * @title: LubanBeanPostProcess
 * @description: TODO
 * @date 2020/11/27
 */

@Component
public class LubanBeanPostProcessor implements BeanPostProcessor {
    public void postProcessBeforeInitialization(String beanName, Object o) {

    }

    public void postProcessAfterInitialization(String beanName, Object o) {
        if("aService".equals(beanName)){
            System.out.println(" A:  LubanBeanPostProcess");
        }
    }
}

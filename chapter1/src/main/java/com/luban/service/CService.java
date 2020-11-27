package com.luban.service;

import com.spring.BeanNameAware;
import com.spring.Component;
import com.spring.Scope;

/**
 * @author Hyy
 * @title: CService
 * @description: TODO
 * @date 2020/11/27
 */

@Component("cService")
@Scope("prototype")
public class CService implements BeanNameAware {

    private String beanName;

    public void setBeanName(String beanName) {
        this.beanName=beanName;
    }
}

package com.spring;

/**
 * @author Hyy
 * @title: BeanDefinition
 * @description: TODO
 * @date 2020/11/30
 */


public class BeanDefinition {
    private String beanName;
    private Class beanClass;
    private boolean lazy;
    private String scope;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

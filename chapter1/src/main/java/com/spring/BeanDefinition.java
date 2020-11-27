package com.spring;

/**
 * @author Hyy
 * @title: BeanDefinition
 * @description: TODO
 * @date 2020/11/27
 */

public class BeanDefinition {
    private boolean lazy;
    private ScopeEnum scope=ScopeEnum.singleton;
    private Class beanClass;

    public ScopeEnum getScope() {
        return scope;
    }

    public void setScope(ScopeEnum scope) {
        this.scope = scope;
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
}

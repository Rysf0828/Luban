package com.luban.service;

import com.spring.Autowired;
import com.spring.Component;
import com.spring.InitializingBean;

/**
 * @author Hyy
 * @title: UserService
 * @description: TODO
 * @date 2020/11/30
 */

@Component("userService")
public class UserService implements InitializingBean {

    @Autowired
    private ProviderService providerService;

    @Override
    public String toString() {
        return "UserService{" +
                "providerService=" + providerService +
                '}';
    }

    public void afterPropertiesSet() {
        System.out.println("initializing bean");
    }
}

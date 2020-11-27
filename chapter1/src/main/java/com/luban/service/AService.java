package com.luban.service;

import com.spring.Autowired;
import com.spring.Component;
import com.spring.InitializingBean;

/**
 * @author Hyy
 * @title: Aservice
 * @description: TODO
 * @date 2020/11/27
 */

@Component("aService")
public class AService implements InitializingBean {

    @Autowired
    private BServive bService;


    public void run(){
        System.out.println("Aservice run..........");
    }

    public void afterPropetiesSet() {
        System.out.println("A： afterPropetiesSet");
    }
}

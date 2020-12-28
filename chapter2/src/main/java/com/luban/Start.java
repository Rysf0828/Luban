package com.luban;

import com.spring.LubanApplicationContext;

/**
 * @author Hyy
 * @title: Start
 * @description: TODO
 * @date 2020/11/30
 */


public class Start {
    public static void main(String[] args) {
        LubanApplicationContext lubanApplicationContext = new LubanApplicationContext(LubanConfig.class);
        System.out.println(lubanApplicationContext.getBean("userService"));
        System.out.println(lubanApplicationContext.getBean("userService"));
        System.out.println(lubanApplicationContext.getBean("providerService"));
        System.out.println(lubanApplicationContext.getBean("customerService"));
        System.out.println(lubanApplicationContext.getBean("customerService"));


    }
}

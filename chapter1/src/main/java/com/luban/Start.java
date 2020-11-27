package com.luban;

import com.spring.LubanApplicationContext;

/**
 * @author Hyy
 * @title: Start
 * @description: TODO
 * @date 2020/11/27
 */

public class Start {
    public static void main(String[] args) {
        LubanApplicationContext applicationContext = new LubanApplicationContext(AppConfig.class);
        System.out.println(applicationContext.getBean("aService"));
        System.out.println(applicationContext.getBean("aService"));
        System.out.println(applicationContext.getBean("bService"));
        System.out.println(applicationContext.getBean("bService"));
        System.out.println(applicationContext.getBean("cService"));
        System.out.println(applicationContext.getBean("cService"));
    }
}

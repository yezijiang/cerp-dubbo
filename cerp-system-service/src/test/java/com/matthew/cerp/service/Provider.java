package com.matthew.cerp.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-30 16:26
 */
public class Provider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        context.start();
        System.in.read(); // 按任意键退出
    }
}

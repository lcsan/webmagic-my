package org.webmagic.spring;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Crawler {

    public static void main(String[] args) {
        AbstractApplicationContext content = new ClassPathXmlApplicationContext("classpath*:crawler.xml");
        content.registerShutdownHook();
    }
}

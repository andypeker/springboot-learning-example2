package com.spring.springboot.appListener;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Frankie Yang on 2018/1/4.
 */
public class MyApplicationPreparedListener implements ApplicationListener<ApplicationPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        System.out.println("**************[ApplicationListener]Frankie Yang 的 Application prepared ！！！");
    }
}

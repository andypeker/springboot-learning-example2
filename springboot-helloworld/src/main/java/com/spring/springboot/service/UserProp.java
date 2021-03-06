package com.spring.springboot.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Frankie Yang on 2018/1/4.
 */
@Component
//通过该注解加入properties文件内的配置
//通过prefix设置properties配置的前缀
@ConfigurationProperties(prefix = "user")
public class UserProp {
    private String userName;
    private Long age;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getAge() {
        return age;
    }
    public void setAge(Long age) {
        this.age = age;
    }
}

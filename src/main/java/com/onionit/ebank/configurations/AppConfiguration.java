package com.onionit.ebank.configurations;

import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class AppConfiguration {
    @Getter
    protected static AppConfiguration singleton;

    @Getter
    protected final BeanFactory beanFactory;

    @Autowired
    public AppConfiguration(BeanFactory beanFactory) {
        if (!Objects.isNull(singleton)) {
            throw new RuntimeException();
        }
        singleton = this;
        this.beanFactory = beanFactory;
    }
}

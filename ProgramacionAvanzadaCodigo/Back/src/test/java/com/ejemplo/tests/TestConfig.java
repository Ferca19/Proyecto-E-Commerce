package com.ejemplo.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TestConfig {
    @Autowired
    public TestConfig(ApplicationContext context, Environment env) {
        System.out.println("Active profiles==========================================================================================================: " + String.join(", ", env.getActiveProfiles()));
    }
}

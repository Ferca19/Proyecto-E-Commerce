package com.ejemplo.tests;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CucumberTestConfig {

    @Bean
    public TestRestTemplate testRestTemplate() {
        return new TestRestTemplate(); // Puedes personalizar la configuración si es necesario
    }
}


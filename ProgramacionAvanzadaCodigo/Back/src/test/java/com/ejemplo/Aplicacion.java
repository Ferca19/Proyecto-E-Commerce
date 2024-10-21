package com.ejemplo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class Aplicacion {
    public static void main(String[] args) {
        SpringApplication.run(Aplicacion.class, args);
    }
}

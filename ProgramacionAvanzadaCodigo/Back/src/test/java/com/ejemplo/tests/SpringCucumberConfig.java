package com.ejemplo.tests;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import programacion.ejemplo.EjemploApplication;
import programacion.ejemplo.EjemploApplicationPruebas;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EjemploApplicationPruebas.class)
@ContextConfiguration(classes = {CucumberTestConfig.class, EjemploApplicationPruebas.class}) // Incluye tu clase principal para asegurarte de que cargue todo correctamente
public class SpringCucumberConfig {
    // Esta clase está vacía, sirve solo como configuración
}

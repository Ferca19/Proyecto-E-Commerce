package com.ejemplo.tests;

import com.ejemplo.Aplicacion;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {CucumberTestConfig.class, Aplicacion.class}) // Incluye tu clase principal para asegurarte de que cargue todo correctamente
public class SpringCucumberConfig {
    // Esta clase está vacía, sirve solo como configuración
}

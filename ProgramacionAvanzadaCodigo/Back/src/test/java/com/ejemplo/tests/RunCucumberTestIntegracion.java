package com.ejemplo.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/ejemplo/features/integracion", // Ruta a los archivos .feature
        glue = "com.ejemplo.tests.integracion", // Paquete donde están las Step Definitions
        plugin = {"pretty", "html:target/cucumber-reports"} // Para generar reportes
)
@SpringBootTest(classes = programacion.ejemplo.EjemploApplicationPruebas.class) // Clase principal de la aplicación
public class RunCucumberTestIntegracion {
}

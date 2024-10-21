package com.ejemplo.tests;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/ejemplo/features", // Ruta a los archivos .feature
        glue = "com.ejemplo.tests", // Paquete donde están las Step Definitions
        plugin = {"pretty", "html:target/cucumber-reports"} // Para generar reportes
)
@SpringBootTest(classes = com.ejemplo.Aplicacion.class) // Clase principal de la aplicación
public class RunCucumberTest {
}

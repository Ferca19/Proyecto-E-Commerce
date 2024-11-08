package com.ejemplo.tests;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import programacion.ejemplo.EjemploApplicationPruebas;

@TestConfiguration
@ContextConfiguration(classes =  EjemploApplicationPruebas.class) // Incluye tu clase principal para asegurarte de que cargue todo correctamente
public class CucumberTestConfig {

}


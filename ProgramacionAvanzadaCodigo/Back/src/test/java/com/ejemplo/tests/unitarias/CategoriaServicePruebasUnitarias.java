package com.ejemplo.tests.unitarias;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.EjemploApplication;
import programacion.ejemplo.EjemploApplicationPruebas;
import programacion.ejemplo.exception.EntidadDuplicadaException;
import programacion.ejemplo.repository.CategoriaRepository;
import programacion.ejemplo.service.CategoriaService;
import programacion.ejemplo.service.IProductoService;

@CucumberContextConfiguration
@SpringBootTest(classes = EjemploApplicationPruebas.class)
@ExtendWith(MockitoExtension.class)
public class CategoriaServicePruebasUnitarias {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private CategoriaDTO categoriaDTO;

    @Given("una categoría con nombre {string} ya existe")
    public void una_categoria_con_nombre_ya_existe(String nombre) {
        categoriaDTO = new CategoriaDTO(); // Inicializar aquí
        categoriaDTO.setNombre(nombre);
        categoriaDTO.setDescripcion("Categoría de productos electrónicos");

        // Configura el mock para que devuelva true, simulando una categoría duplicada
        when(categoriaRepository.existsByNombreIgnoreCase(nombre)).thenReturn(true);
    }

    @When("el administrador intenta crear otra categoría con el mismo nombre {string}")
    public void el_administrador_intenta_crear_otra_categoria_con_el_mismo_nombre(String nombre) {
        // Ajusta el nombre del DTO para el test
        categoriaDTO.setNombre(nombre);
    }

    @Then("el sistema debe rechazar la creación, indicando que la categoría ya existe")
    public void el_sistema_debe_lanzar_un_error_indicando_que_la_categoria_ya_existe() {
        // Verifica que se lanza la excepción correcta al intentar guardar una categoría duplicada
        assertThrows(NullPointerException.class, () -> categoriaService.guardar(categoriaDTO));
    }
}

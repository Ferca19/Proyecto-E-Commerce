package com.ejemplo.tests.sistema;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.service.IProductoService;
import programacion.ejemplo.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ProductoPruebasSistema {

    private static final Logger logger = LoggerFactory.getLogger(ProductoPruebasSistema.class);

    @Autowired
    private ProductoService productoService; // Inyección del servicio

    private ProductoDTO productoDTO;
    private String saveResponse;

    @Given("un producto con precio {string}")
    public void un_producto_con_precio(String precio) {
        productoDTO = new ProductoDTO();
        productoDTO.setNombre("Producto de Prueba");
        productoDTO.setDescripcion("Descripcion de Prueba");
        productoDTO.setPrecio(Double.parseDouble(precio));
        productoDTO.setStock(20);
        productoDTO.setTamano("xl");
        productoDTO.setColor("blanco");
        productoDTO.setImagen("imagen");
        productoDTO.setCategoriaId(1);       // ID de categoría válida para la prueba
        productoDTO.setSubcategoriaId(1);    // ID de subcategoría válida para la prueba
        productoDTO.setMarcaId(1);           // ID de marca válida para la prueba
    }

    @When("el administrador intenta guardar el producto")
    public void el_administrador_intenta_guardar_el_producto() {
        logger.info("Intentando guardar el producto: {}", productoDTO);
        try {

            ProductoDTO resultado = productoService.createProducto(productoDTO);
            saveResponse = "Producto guardado con éxito";
            logger.info("Producto guardado exitosamente: {}", resultado);
        } catch (IllegalArgumentException e) {
            saveResponse = e.getMessage();
            logger.error("Error al guardar el producto: {}", e.getMessage());
        } catch (RuntimeException e) {
            saveResponse = "Categoría, subcategoría o marca no encontrada";
            logger.error("Error al guardar el producto: {}", e.getMessage());
        }
    }

    @Then("el sistema debe responder con {string}")
    public void el_sistema_debe_responder_con(String resultadoEsperado) {
        logger.info("Esperado: {}, Obtenido: {}", resultadoEsperado, saveResponse);
        assertEquals(resultadoEsperado, saveResponse);
    }
}

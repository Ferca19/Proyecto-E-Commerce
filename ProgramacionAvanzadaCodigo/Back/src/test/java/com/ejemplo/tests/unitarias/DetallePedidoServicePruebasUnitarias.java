package com.ejemplo.tests.unitarias;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.EjemploApplicationPruebas;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.EstadoRepository;
import programacion.ejemplo.repository.PedidoRepository;
import programacion.ejemplo.repository.UsuarioRepository;
import programacion.ejemplo.service.DetallePedidoService;
import programacion.ejemplo.service.PedidoService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest(classes = EjemploApplicationPruebas.class)
@ExtendWith(MockitoExtension.class)
public class DetallePedidoServicePruebasUnitarias {


    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EstadoRepository estadoRepository;


    private List<DetallePedidoDTO> detallesPedido;
    private Pedido pedidoCreado;
    private Usuario usuario;
    private Exception exception;
    private double importeCalculado;
    private boolean exceptionEsperada = false; // Variable para manejar si se espera una excepción



    //====================== Scenario: Crear detalle de pedido con producto válido y stock suficiente ======================


    @Given("un pedido válido existe")
    public void un_pedido_valido_existe() {
        // Crear un usuario válido
        usuario = new Usuario();
        usuario.setId(3);
        usuario.setNombre("Nombre Usuario");


        // Buscar un estado existente
        Estado estado = estadoRepository.findByIdAndEliminado(1,0); // Cambia según tu lógica

        // Crear un pedido válido utilizando las entidades existentes
        pedidoCreado = new Pedido();
        pedidoCreado.setFechaYHora(new Date()); // Fecha y hora actuales
        pedidoCreado.setImporteTotal(0.0); // Inicia en 0, se calculará después
        pedidoCreado.setEliminado(Pedido.NO); // Pedido no eliminado
        pedidoCreado.setUsuario(usuario); // Relación con el usuario existente
        pedidoCreado.setEstado(estado); // Relación con el estado existente
        pedidoCreado.setDetallesPedido(new ArrayList<>()); // Lista inicial vacía de detalles

        // Guardar el pedido en la base de datos
        pedidoCreado = pedidoRepository.save(pedidoCreado);
    }


    @Given("los detalles son:")
    public void los_detalles_del_pedido_son(DataTable dataTable) {
        if (detallesPedido == null) {
            detallesPedido = new ArrayList<>();
        }

        // Convertir la tabla en una lista de mapas para procesar cada fila
        List<Map<String, String>> detalles = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> detalle : detalles) {
            Integer productoId = Integer.parseInt(detalle.get("productoId"));
            Integer cantidad = Integer.parseInt(detalle.get("cantidad"));

            DetallePedidoDTO detallePedidoDTO = new DetallePedidoDTO();
            detallePedidoDTO.setProductoId(productoId);
            detallePedidoDTO.setCantidad(cantidad);
            detallesPedido.add(detallePedidoDTO);
        }
    }

    @When("se intenta crear los detalles del pedido")
    public void se_intenta_crear_los_detalles_del_pedido() {
        if (exceptionEsperada) {
            // Se espera una excepción, capturarla
            exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                importeCalculado = detallePedidoService.crearDetallePedido(pedidoCreado, detallesPedido);
            });
        } else {
            // No se espera ninguna excepción, simplemente ejecutamos el método
            try {
                importeCalculado = detallePedidoService.crearDetallePedido(pedidoCreado, detallesPedido);
            } catch (Exception e) {
                // Si se lanza cualquier excepción, la prueba falla
                Assertions.fail("No se esperaba una excepción, pero ocurrió: " + e.getMessage());
            }
        }
    }

    @Then("los detalles deben ser creados correctamente")
    public void los_detalles_deben_ser_creados_correctamente() {
        // Verificar que los detalles fueron agregados al pedido
        assertNotNull(pedidoCreado.getDetallesPedido());
        assertEquals(detallesPedido.size(), pedidoCreado.getDetallesPedido().size());
    }

    @Then("el importe total debe ser {double}")
    public void el_importe_total_debe_ser(Double importeEsperado) {
        // Verificar que el importe total sea el esperado
        assertEquals(importeEsperado, importeCalculado);
    }

    //====================== Scenario: Producto Válido con Stock Insuficiente ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un pedido válido existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles son:

    @Given("se espera que se lance una excepción")
    public void se_espera_que_se_lance_una_excepcion() {
        exceptionEsperada = true;  // Indicar que se espera una excepción
    }

    // Este paso ya existe por lo que lo reutilizamos: se intenta crear los detalles del pedido

    @Then("se debe lanzar una excepción IllegalArgumentException con el mensaje {string}")
    public void se_debe_lanzar_una_excepcion_con_el_mensaje(String mensajeEsperado) {
        // Verificar que la excepción lanzada tenga el mensaje esperado
        Assertions.assertEquals(mensajeEsperado, exception.getMessage());
    }

    //====================== Scenario: Producto No Encontrado ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un pedido válido existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles son:
    // Este paso ya existe por lo que lo reutilizamos: Given se espera que se lance una excepcion
    // Este paso ya existe por lo que lo reutilizamos: se intenta crear los detalles del pedido
    // Este paso ya existe por lo que lo reutilizamos: Then se debe lanzar una excepción IllegalArgumentException con el mensaje

    //====================== Scenario: Detalle de Pedido con Cantidad Cero ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un pedido válido existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles son:
    // Este paso ya existe por lo que lo reutilizamos: Given se espera que se lance una excepcion
    // Este paso ya existe por lo que lo reutilizamos: se intenta crear los detalles del pedido
    // Este paso ya existe por lo que lo reutilizamos: Then se debe lanzar una excepción IllegalArgumentException con el mensaje

    //====================== Scenario: Varios Productos Válidos ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un pedido válido existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles son:
    // Este paso ya existe por lo que lo reutilizamos: se intenta crear los detalles del pedido
    // Este paso ya existe por lo que lo reutilizamos: Then los detalles deben ser creados correctamente
    // Este paso ya existe por lo que lo reutilizamos: And el importe total debe ser


}

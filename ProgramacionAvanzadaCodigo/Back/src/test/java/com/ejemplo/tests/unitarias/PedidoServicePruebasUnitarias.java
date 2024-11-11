package com.ejemplo.tests.unitarias;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.*;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.EjemploApplicationPruebas;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.service.*;


@SpringBootTest(classes = EjemploApplicationPruebas.class)
@ExtendWith(MockitoExtension.class)
public class PedidoServicePruebasUnitarias {

    @Autowired
    private PedidoService pedidoService;


    private List<DetallePedidoDTO> detallesPedido;
    private Pedido pedidoCreado;
    private Usuario usuario;
    private Usuario usuarioNulo;
    private Exception exception;



    //====================== Scenario: Crear pedido con un usuario ID válido y detalles no vacíos ======================

    @Given("un usuario con ID {int} existe")
    public void un_usuario_con_ID_existe(Integer id) {
        // Simular el objeto Usuario
        usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Nombre Usuario");

    }

    @Given("los detalles del pedido son:")
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

    @When("se intenta crear un pedido con el usuario y la lista de detalles")
    public void se_intenta_crear_un_pedido_con_el_usuario_y_la_lista_de_detalles() {
        // Simula el estado inicial como un objeto de tipo Estado
        Estado estadoPendiente = new Estado();
        estadoPendiente.setNombre("Pendiente");

        // Llamar al método que estamos probando
        pedidoCreado = pedidoService.crearPedido(usuario, detallesPedido);
    }

    @Then("el pedido debe ser creado con su estado inicial")
    public void el_pedido_debe_ser_creado_con_estado_inicial() {
        // Verificar que el pedido tiene el estado inicial asignado
        assertEquals("Pendiente", pedidoCreado.getEstado().getNombre());
    }

    @Then("el importe total del pedido debe estar calculado")
    public void el_importe_total_del_pedido_debe_estar_calculado() {
        // Verificar que el importe total fue calculado y asignado
        assertEquals(100000.0, pedidoCreado.getImporteTotal());
    }

    //====================== Scenario: Crear un pedido con usuario nulo ======================

    @Given("un usuario nulo existe")
    public void un_usuario_nulo_existe() {
        // Simular el objeto Usuario
        usuarioNulo = null;
    }

    // Este paso ya existe por lo que lo reutilizamos: Given los detalles del pedido son:


    @When("se intenta crear un pedido con un usuario nulo y los detalles del pedido")
    public void se_intenta_crear_un_pedido_con_un_usuario_nulo_y_los_detalles_del_pedido() {
        exception = assertThrows(
                RuntimeException.class,
                () -> pedidoService.crearPedido(usuarioNulo, detallesPedido)
        );
    }

    @Then("se debe lanzar una excepción indicando {string}")
    public void se_debe_lanzar_una_excepción_indicando(String mensajeEsperado) {
        assertEquals(mensajeEsperado, exception.getMessage());
    }

    //====================== Scenario: Crear pedido con un usuario ID válido y lista de detalles vacía ======================

    @Given("una lista de detalles de pedido vacía")
    public void una_lista_de_detalles_de_pedido_vacía() {
        detallesPedido = new ArrayList<>();
    }

    @When("se intenta crear un pedido con el usuario y la lista de detalles vacía")
    public void se_intenta_crear_un_pedido_con_el_usuario_y_la_lista_de_detalles_vacía() {

        exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoService.crearPedido(usuario, detallesPedido)
        );

    }

    // Este paso ya existe por lo que lo reutilizamos: @Then("se debe lanzar una excepción indicando {string}")

    //====================== Scenario: Crear pedido con un usuario válido y detalle de producto con cantidad cero ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe

    @Given("los detalles del pedido con cantidad de producto 0 o negativa son:")
    public void los_detalles_del_pedido_con_cantidad_de_producto_0_son(DataTable dataTable) {
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

    @When("se intenta crear un pedido con el usuario y el detalle de pedido con cantidad 0")
    public void se_intenta_crear_un_pedido_con_el_usuario_y_el_detalle_de_pedido() {
        // Asigna la excepción a la variable de clase
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoService.crearPedido(usuario, detallesPedido)
        );
    }


    // Este paso ya existe por lo que lo reutilizamos: @Then("se debe lanzar una excepción indicando {string}")


    //====================== Scenario: Scenario: Crear pedido con un pedido con un producto no disponible ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:

    @When("se intenta crear un pedido con el usuario y la lista de detalles pero con un producto no disponible")
    public void se_intenta_crear_un_pedido_con_el_usuario_y_la_lista_de_detalles_pero_con_un_producto_no_disponible() {
        exception = assertThrows(
                RuntimeException.class,
                () -> pedidoService.crearPedido(usuario, detallesPedido)
        );
    }

    // Este paso ya existe por lo que lo reutilizamos: @Then("se debe lanzar una excepción indicando {string}")


}

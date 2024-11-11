package com.ejemplo.tests.aceptacion;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.EjemploApplicationPruebas;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.service.PedidoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@CucumberContextConfiguration
@SpringBootTest(classes = EjemploApplicationPruebas.class)
@ExtendWith(MockitoExtension.class)
public class PedidoServicePruebasAceptacion {

    @Autowired
    private PedidoService pedidoService;


    private List<DetallePedidoDTO> detallesPedido;
    private Pedido pedidoCreado;
    private Usuario usuario;
    private double importeTotalCalculado;
    private Usuario usuarioNulo;
    private Exception exception;



    //====================== Scenario: Creación de un pedido con éxito ======================

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

        // Llamar al método que estamos probando
        pedidoCreado = pedidoService.crearPedido(usuario, detallesPedido);
    }

    @Then("el pedido se crea exitosamente")
    public void el_pedido_debe_ser_creado_exitosamente() {
        // Verificamos que el pedido no sea nulo y que haya sido creado correctamente
        assertNotNull(pedidoCreado);
    }

    @Then("la respuesta debe contener los detalles del pedido")
    public void la_respuesta_debe_contener_los_detalles_del_pedido() {
        // Verificamos que el pedido contiene los detalles correctos (productos y cantidades)
        assertEquals(1, pedidoCreado.getDetallesPedido().size());  // Asumiendo que hay dos productos
        assertEquals(1, pedidoCreado.getDetallesPedido().get(0).getProducto().getId());
        assertEquals(2, pedidoCreado.getDetallesPedido().get(0).getCantidad());
    }

    //====================== Scenario: Validación de Estado del Pedido ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe

    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:

    // Este paso ya existe por lo que lo reutilizamos:  When se intenta crear un pedido con el usuario y la lista de detalles

    // Este paso ya existe por lo que lo reutilizamos: Then el pedido se crea exitosamente

    @Then("el estado del pedido debe ser {string}")
    public void el_estado_del_pedido_debe_ser(String estadoEsperado) {
        // Verificamos que el estado del pedido sea "Pendiente"
        assertEquals(estadoEsperado, pedidoCreado.getEstado().getNombre());
    }

    //====================== Scenario: Cálculo Correcto del Importe Total ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe

    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:

    // Este paso ya existe por lo que lo reutilizamos:  When se intenta crear un pedido con el usuario y la lista de detalles

    // Este paso ya existe por lo que lo reutilizamos: Then el pedido se crea exitosamente

    @Then("el importe total del pedido debe estar calculado correctamente")
    public void el_importe_total_del_pedido_debe_establecerse() {
        // Verificamos que el importe total haya sido calculado correctamente

        importeTotalCalculado = pedidoCreado.getDetallesPedido().get(0).getCantidad() * pedidoCreado.getDetallesPedido().get(0).getProducto().getPrecio();
        assertTrue(pedidoCreado.getImporteTotal() == importeTotalCalculado);
    }


    //====================== Scenario: Crear un pedido con usuario nulo ======================

    @Given("un usuario nulo existe")
    public void un_usuario_nulo_existe() {
        // Simular el objeto Usuario
        usuarioNulo = null;
    }

    // Este paso ya existe por lo que lo reutilizamos: @Given("los detalles del pedido son:")


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


    //====================== Scenario:  ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe

    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:

    @When("se intenta crear un pedido con el usuario y los detalles no validos")
    public void se_intenta_crear_un_pedido_con_el_usuario_y_los_detalles_no_validos() {
        exception = assertThrows(
                RuntimeException.class,
                () -> pedidoService.crearPedido(usuario, detallesPedido)
        );
    }

    // Este paso ya existe por lo que lo reutilizamos: Then se debe lanzar una excepción indicando

}

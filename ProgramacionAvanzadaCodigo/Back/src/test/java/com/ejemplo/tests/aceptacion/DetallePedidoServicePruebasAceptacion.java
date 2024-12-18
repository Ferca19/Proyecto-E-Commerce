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
import programacion.ejemplo.model.DetallePedido;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.DetallePedidoRepository;
import programacion.ejemplo.service.PedidoService;
import programacion.ejemplo.service.ProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = EjemploApplicationPruebas.class)
@ExtendWith(MockitoExtension.class)
public class DetallePedidoServicePruebasAceptacion {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;


    private List<DetallePedidoDTO> detallesPedido;
    private Pedido pedidoCreado;
    private Usuario usuario;
    private double importeTotalCalculado;
    private Usuario usuarioNulo;
    private Exception exception;
    private Pedido pedidoRecuperado;
    private List<DetallePedido> detallesAnteriores;
    private List<DetallePedido> detallesGuardados;
    private int productoIdRestarStock;
    private int cantidadRestarStock;
    private int stockAnteriorProducto;
    private int posibleStock;



    //====================== Scenario: Creación de un pedido con éxito ======================

    @Given("un usuario con ID {int} existe #3")
    public void un_usuario_con_ID_existe(Integer id) {
        // Simular el objeto Usuario
        usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Nombre Usuario");

    }

    @Given("los detalles del pedido son: #3")
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

    @When("se crea un pedido con el usuario y los detalles del pedido #3")
    public void se_crea_un_pedido_con_el_usuario_y_los_detalles_del_pedido() {

        // Mockear el estado inicial para el pedido
        Estado estadoPendiente = new Estado();
        estadoPendiente.setNombre("Pendiente");

        productoIdRestarStock = detallesPedido.get(0).getProductoId();
        stockAnteriorProducto = productoService.obtenerObjetoPorId(productoIdRestarStock).getStock();
        cantidadRestarStock = detallesPedido.get(0).getCantidad();

        detallesAnteriores = detallePedidoRepository.findAll();

        // Llamar al método que estamos probando
        pedidoCreado = pedidoService.crearPedido(usuario, detallesPedido);
    }


    @Then("el pedido debe ser creado con los detalles asignados #3")
    public void el_pedido_debe_ser_creado_con_los_detalles_asignados() {
        assertNotNull(pedidoCreado);
        assertNotNull(pedidoCreado.getDetallesPedido());
        assertEquals(detallesPedido.size(), pedidoCreado.getDetallesPedido().size());
    }

    @Then("la respuesta debe contener los detalles del pedido #2")
    public void la_respuesta_debe_contener_los_detalles_del_pedido() {
        // Verificamos que el pedido contiene los detalles correctos (productos y cantidades)
        assertEquals(1, pedidoCreado.getDetallesPedido().size());
        assertEquals(1, pedidoCreado.getDetallesPedido().get(0).getProducto().getId());
        assertEquals(2, pedidoCreado.getDetallesPedido().get(0).getCantidad());
    }

    //====================== Scenario: Calcular el importe total del pedido correctamente ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe

    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:

    // Este paso ya existe por lo que lo reutilizamos: When se crea un pedido con el usuario y los detalles del pedido #3

    @Then("el importe total del pedido debe estar calculado correctamente, siendo {double} #2")
    public void el_importe_total_del_pedido_debe_estar_calculado_correctamente(double importeEsperado) {
        // Verificar que el importe total fue calculado y asignado
        assertEquals(importeEsperado, pedidoCreado.getImporteTotal());
    }


    //====================== Scenario: Manejo de Producto No Encontrado ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe

    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:

    @When("se intenta crear un pedido con el usuario y la lista de detalles pero con un producto no existente #2")
    public void se_intenta_crear_un_pedido_con_el_usuario_y_la_lista_de_detalles_pero_con_un_producto_no_disponible() {
        exception = assertThrows(
                RuntimeException.class,
                () -> pedidoService.crearPedido(usuario, detallesPedido)
        );
    }

    @Then("se debe lanzar una excepción indicando {string} #2")
    public void se_debe_lanzar_una_excepción_indicando(String mensajeEsperado) {
        assertEquals(mensajeEsperado, exception.getMessage());
    }

    //====================== Scenario: Manejo de Stock Insuficiente ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe

    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:


    @When("se intenta crear un pedido con el usuario y los detalles del pedido #2")
    public void se_intenta_crear_un_pedido_con_el_usuario_y_los_detalles() {
        exception = assertThrows(
                RuntimeException.class,
                () -> pedidoService.crearPedido(usuario, detallesPedido)
        );
    }

    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:


    //====================== Scenario: Detalle de Pedido con Cantidad Cero ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe

    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:

    // Este paso ya existe por lo que lo reutilizamos: When se intenta crear un pedido con el usuario y los detalles del pedido #2

    // Este paso ya existe por lo que lo reutilizamos: Then se debe lanzar una excepción indicando #2


}

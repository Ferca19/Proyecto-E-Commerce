package com.ejemplo.tests.integracion;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.EjemploApplicationPruebas;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.PedidoRepository;
import programacion.ejemplo.service.PedidoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(classes = EjemploApplicationPruebas.class)
public class PedidoPruebasIntegracion {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;


    private Usuario usuario;
    private List<DetallePedidoDTO> detallesPedido;
    private Pedido pedidoCreado;
    private Pedido pedidoRecuperado;
    private Exception exception;


    //====================== Scenario: Crear un pedido con un usuario válido y detalles de pedido ======================


    @Given("un usuario con ID {int} existe")
    public void un_usuario_con_ID_y_nombre_existe_en_el_sistema(Integer id) {
        usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Juan Perez");

    }

    @Given("los detalles del pedido son:")
    public void los_detalles_del_pedido_son(DataTable dataTable) {
        detallesPedido = new ArrayList<>();

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

    @When("se crea un pedido con el usuario y los detalles del pedido")
    public void se_crea_un_pedido_con_el_usuario_y_los_detalles_del_pedido() {

        // Mockear el estado inicial para el pedido
        Estado estadoPendiente = new Estado();
        estadoPendiente.setNombre("Pendiente");

        // Llamar al método que estamos probando
        pedidoCreado = pedidoService.crearPedido(usuario, detallesPedido);
    }

    @Then("el pedido debe ser creado con el usuario asignado")
    public void el_pedido_debe_ser_creado_con_el_usuario_asignado() {


        assertEquals(usuario.getId(), pedidoCreado.getUsuario().getId());
        assertEquals(usuario.getNombre(), pedidoCreado.getUsuario().getNombre());
    }

    @Then("el importe total del pedido debe estar calculado correctamente, siendo {double}")
    public void el_importe_total_del_pedido_debe_estar_calculado_correctamente(double importeEsperado) {
        // Verificar que el importe total fue calculado y asignado
        assertEquals(importeEsperado, pedidoCreado.getImporteTotal());
    }

    //====================== Scenario: Scenario: Cálculo correcto de detalles del pedido ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:
    // Este paso ya existe por lo que lo reutilizamos: se crea un pedido con el usuario y los detalles del pedido

    @Then("el pedido debe ser creado con los detalles asignados")
    public void el_pedido_debe_ser_creado_con_los_detalles_asignados() {
        assertNotNull(pedidoCreado);
        assertNotNull(pedidoCreado.getDetallesPedido());
        assertEquals(detallesPedido.size(), pedidoCreado.getDetallesPedido().size());
    }

    // Este paso ya existe por lo que lo reutilizamos: And el importe total del pedido debe estar calculado correctamente, siendo 220000.0


    //====================== Scenario: Guardado correcto del pedido en la base de datos ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:
    // Este paso ya existe por lo que lo reutilizamos: se crea un pedido con el usuario y los detalles del pedido

    @Then("el pedido debe ser guardado en la base de datos")
    public void el_pedido_debe_ser_guardado_en_la_base_de_datos() {
        // Verificar que el pedido no es nulo
        assertNotNull(pedidoCreado);

        // Buscar el pedido en el repositorio para comprobar que fue guardado
        Optional<Pedido> pedidoGuardado = pedidoRepository.findById(pedidoCreado.getId());

        // Verificar que el pedido se guardó en la base de datos
        assertTrue(pedidoGuardado.isPresent(), "El pedido no fue encontrado en la base de datos");

    }


    @Then("los datos del pedido guardado deben ser correctos, siendo el importe total {double}")
    public void los_datos_del_pedido_guardado_deben_ser_correctos(double importeEsperado) {
        assertNotNull(pedidoCreado);
        assertEquals(usuario.getId(), pedidoCreado.getUsuario().getId());
        assertEquals(importeEsperado, pedidoCreado.getImporteTotal());
        assertNotNull(pedidoCreado.getFechaYHora());
    }


    //====================== Scenario: Recuperar un pedido después de crearlo ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:
    // Este paso ya existe por lo que lo reutilizamos: se crea un pedido con el usuario y los detalles del pedido

    @Then("el pedido debe ser recuperado correctamente desde el repositorio")
    public void el_pedido_debe_ser_recuperado_correctamente_desde_el_repositorio() {
        assertNotNull(pedidoCreado, "El pedido creado no debe ser nulo");

        pedidoRecuperado = pedidoRepository.findById(pedidoCreado.getId())
                .orElseThrow(() -> new RuntimeException("El pedido no se pudo recuperar de la base de datos"));

        assertNotNull(pedidoRecuperado, "El pedido recuperado no debe ser nulo");
        assertEquals(pedidoCreado.getId(), pedidoRecuperado.getId(), "El ID del pedido recuperado debe coincidir con el creado");
        assertEquals(pedidoCreado.getUsuario().getId(), pedidoRecuperado.getUsuario().getId(), "El usuario del pedido debe coincidir");
        assertEquals(pedidoCreado.getImporteTotal(), pedidoRecuperado.getImporteTotal(), "El importe total debe coincidir");
    }


    //======================  Scenario: No guardar pedido si ocurre un error en la creación de detalles ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:

    @When("se intenta crear un pedido con el usuario y la lista de detalles inválidos")
    public void se_intenta_crear_un_pedido_con_el_usuario_y_la_lista_de_detalles_invalidos() {
        try {
            pedidoService.crearPedido(usuario, detallesPedido);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("se debe lanzar una excepción")
    public void se_debe_lanzar_una_excepcion() {
        assertNotNull(exception, "Se esperaba una excepción, pero no fue lanzada.");
        assertTrue(exception instanceof RuntimeException, "La excepción debe ser del tipo RuntimeException.");
        assertEquals("Producto no encontrado", exception.getMessage(), "El mensaje de error debe ser correcto.");
    }

    @Then("el pedido no debe estar guardado en la base de datos")
    public void el_pedido_no_debe_estar_guardado_en_la_base_de_datos() {
        // Consultar la base de datos para verificar si se agregó un nuevo pedido
        boolean pedidoExiste = pedidoRepository.findAll().stream()
                .anyMatch(pedido -> pedido.getUsuario().equals(usuario) && pedido.getDetallesPedido().isEmpty());

        assertFalse(pedidoExiste, "El pedido inválido no debe haber sido guardado en la base de datos.");
    }








}
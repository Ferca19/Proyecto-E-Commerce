package com.ejemplo.tests.integracion;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.EjemploApplicationPruebas;
import programacion.ejemplo.model.DetallePedido;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.DetallePedidoRepository;
import programacion.ejemplo.repository.PedidoRepository;
import programacion.ejemplo.service.PedidoService;
import programacion.ejemplo.service.ProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = EjemploApplicationPruebas.class)
public class DetallePedidoPruebasIntegracion {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;


    private Usuario usuario;
    private List<DetallePedidoDTO> detallesPedido;
    private Pedido pedidoCreado;
    private Pedido pedidoRecuperado;
    private List<DetallePedido> detallesAnteriores;
    private List<DetallePedido> detallesGuardados;
    private Exception exception;
    private int productoIdRestarStock;
    private int cantidadRestarStock;
    private int stockAnteriorProducto;
    private int posibleStock;


    //====================== Scenario: Integración con ProductoService para un producto válido ======================


    @Given("un usuario con ID {int} existe #2")
    public void un_usuario_con_ID_y_nombre_existe_en_el_sistema(Integer id) {
        usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Juan Perez");

    }

    @Given("los detalles del pedido son: #2")
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

    @When("se crea un pedido con el usuario y los detalles del pedido #2")
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


    @Then("el detalle se crea con el producto correspondiente al ID proporcionada: {int}")
    public void el_detalle_se_crea_con_el_producto_correspondiente_al_ID_proporcionada(Integer productoId) {
        // Verificar que productoService.obtenerPorId se llama con el producto ID esperado
        assertEquals(productoId, pedidoCreado.getDetallesPedido().get(0).getProducto().getId());
    }

    @Then("el pedido debe ser creado correctamente")
    public void el_pedido_debe_ser_creado_correctamente() {
        assertNotNull(pedidoCreado, "El pedido creado no debe ser nulo");
        assertEquals(usuario.getId(), pedidoCreado.getUsuario().getId(), "El ID del usuario debe coincidir");
        assertFalse(pedidoCreado.getDetallesPedido().isEmpty(), "El pedido debe contener detalles de pedido");
    }


    @Then("el importe total del pedido debe estar calculado correctamente, siendo {double} #2")
    public void el_importe_total_del_pedido_debe_estar_calculado_correctamente(double importeEsperado) {
        // Verificar que el importe total fue calculado y asignado
        assertEquals(importeEsperado, pedidoCreado.getImporteTotal());
    }

    //====================== Scenario: Integración con ProductoService para un producto no existente ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe #2
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son: #2

    @When("se intenta crear un pedido con el usuario y la lista de detalles pero con un producto no existente")
    public void se_intenta_crear_un_pedido_con_el_usuario_y_la_lista_de_detalles_pero_con_un_producto_no_disponible() {
        exception = assertThrows(
                RuntimeException.class,
                () -> pedidoService.crearPedido(usuario, detallesPedido)
        );
    }

    @Then("se debe lanzar una excepción indicando {string}")
    public void se_debe_lanzar_una_excepción_indicando(String mensajeEsperado) {
        assertEquals(mensajeEsperado, exception.getMessage());
    }


    @Then("el pedido no debe estar guardado en la base de datos #2")
    public void el_pedido_no_debe_estar_guardado_en_la_base_de_datos() {
        // Consultar la base de datos para verificar si se agregó un nuevo pedido
        boolean pedidoExiste = pedidoRepository.findAll().stream()
                .anyMatch(pedido -> pedido.getUsuario().equals(usuario) && pedido.getDetallesPedido().isEmpty());

        assertFalse(pedidoExiste, "El pedido inválido no debe haber sido guardado en la base de datos.");
    }

    //====================== Scenario: Integración con Ajustar Inventario ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe #2
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son: #2
    // Este paso ya existe por lo que lo reutilizamos: se crea un pedido con el usuario y los detalles del pedido #2

    @Then("se debe ajustar correctamente el stock de cada producto")
    public void se_debe_ajustar_correctamente_el_stock_de_cada_producto() {
        // Imprimir los valores antes de la aserción
        System.out.println("Stock antes del pedido: " + stockAnteriorProducto);
        System.out.println("Cantidad a restar del stock: " + cantidadRestarStock);
        System.out.println("Stock del producto después del pedido: " + productoService.obtenerObjetoPorId(productoIdRestarStock).getStock());

        posibleStock = stockAnteriorProducto - cantidadRestarStock;
        // Realizar la comparación
        assertEquals(
                productoService.obtenerObjetoPorId(productoIdRestarStock).getStock(),
                posibleStock,
                "El stock no se ajustó correctamente"
        );
    }

    @Then("el pedido debe ser creado con los detalles asignados #2")
    public void el_pedido_debe_ser_creado_con_los_detalles_asignados() {
        assertNotNull(pedidoCreado);
        assertNotNull(pedidoCreado.getDetallesPedido());
        assertEquals(detallesPedido.size(), pedidoCreado.getDetallesPedido().size());
    }

    // Este paso ya existe por lo que lo reutilizamos: el importe total del pedido debe estar calculado correctamente, siendo 220000.0 #2


    //====================== Scenario: Guardado del detalle de pedido en el repositorio ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe #2
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son: #2
    // Este paso ya existe por lo que lo reutilizamos: se crea un pedido con el usuario y los detalles del pedido #2

    @Then("el detalle de cada pedido debe ser guardado en el repositorio correctamente")
    public void el_detalle_de_cada_pedido_debe_ser_guardado_en_el_repositorio_correctamente() {

        // Recuperar los detalles del pedido desde el repositorio
        detallesGuardados = detallePedidoRepository.findAll();

        // Verificar que el número de detalles guardados coincide con el número de detalles creados
        assertEquals((detallesAnteriores.size()+detallesPedido.size()), detallesGuardados.size(), "El número de detalles guardados no coincide.");

        // Iterar y verificar cada detalle guardado
        for (DetallePedidoDTO detalleDTO : detallesPedido) {
            // Buscar el detalle en la base de datos por productoId y cantidad
            boolean encontrado = detallesGuardados.stream().anyMatch(detalleGuardado ->
                    detalleGuardado.getProducto().getId().equals(detalleDTO.getProductoId()) &&
                            detalleGuardado.getCantidad().equals(detalleDTO.getCantidad())
            );

            // Afirmar que cada detalle se encuentra correctamente guardado
            assertTrue(encontrado, "El detalle con productoId: " + detalleDTO.getProductoId() +
                    " y cantidad: " + detalleDTO.getCantidad() + " no fue encontrado en el repositorio.");
        }

        System.out.println("Todos los detalles del pedido se guardaron correctamente en el repositorio.");
    }


    // Este paso ya existe por lo que lo reutilizamos: And el pedido debe ser creado con los detalles asignados #2


    //====================== Scenario: Guardado de múltiples detalles de pedido en una transacción ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe #2
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son: #2
    // Este paso ya existe por lo que lo reutilizamos: se crea un pedido con el usuario y los detalles del pedido #2
    // Este paso ya existe por lo que lo reutilizamos: Then el detalle de cada pedido debe ser guardado en el repositorio correctamente

    @Then("todos los detalles del pedido deben estar asociados al mismo pedido")
    public void todos_los_detalles_del_pedido_deben_estar_asociados_al_mismo_pedido() {

        // Recuperar todos los detalles guardados en el repositorio
        List<DetallePedido> detallesGuardados = detallePedidoRepository.findAll();

        // Filtrar los N últimos detalles creados (N = cantidad de detalles creados en el pedido actual)
        int cantidadDetallesCreados = detallesPedido.size();
        List<DetallePedido> ultimosDetalles = detallesGuardados
                .subList(detallesGuardados.size() - cantidadDetallesCreados, detallesGuardados.size());

        // Verificar que los últimos detalles están asociados al mismo pedido
        Integer pedidoIdAsociado = pedidoCreado.getId();
        boolean todosAsociados = ultimosDetalles.stream()
                .allMatch(detalle -> detalle.getPedido().getId().equals(pedidoIdAsociado));

        // Afirmación de que todos los detalles están correctamente asociados
        assertTrue(todosAsociados, "No todos los últimos detalles están asociados al mismo pedido con ID: " + pedidoIdAsociado);

    }



}
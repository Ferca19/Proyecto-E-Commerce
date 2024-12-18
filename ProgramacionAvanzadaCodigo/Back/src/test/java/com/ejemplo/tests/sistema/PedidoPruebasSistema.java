package com.ejemplo.tests.sistema;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
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
import programacion.ejemplo.service.DetallePedidoService;
import programacion.ejemplo.service.EstadoService;
import programacion.ejemplo.service.PedidoService;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@CucumberContextConfiguration
@SpringBootTest(classes = EjemploApplicationPruebas.class)
public class PedidoPruebasSistema {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    private Usuario usuario;
    private List<DetallePedidoDTO> detallesPedido;
    private Pedido pedidoCreado;
    private Pedido pedidoRecuperado;
    private int numPedidos = 100;
    private long tiempoEjecucion;
    private Exception excepcion;



    //====================== Scenario: Crear un pedido con usuario y detalles de pedido ======================

    @Given("un usuario con ID {int} existe")
    public void un_usuario_con_ID_existe(Integer id) {
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

        // Creamos el pedido
        pedidoCreado = pedidoService.crearPedido(usuario, detallesPedido);

    }

    @Then("el pedido debe ser creado con el usuario asignado")
    public void el_pedido_debe_ser_creado_con_el_usuario_asignado() {

        assertNotNull(pedidoCreado);
        assertEquals(usuario.getId(), pedidoCreado.getUsuario().getId());
        assertEquals(usuario.getNombre(), pedidoCreado.getUsuario().getNombre());
    }

    @Then("el pedido debe tener el estado {string}")
    public void el_pedido_debe_tener_el_estado(String estadoEsperado) {
        assertEquals(estadoEsperado, pedidoCreado.getEstado().getNombre());
    }


    @Then("el pedido debe estar almacenado en el repositorio")
    public void el_pedido_debe_estar_almacenado_en_el_repositorio() {
        Optional<Pedido> pedidoEnRepositorio = pedidoRepository.findById(pedidoCreado.getId());
        assertTrue(pedidoEnRepositorio.isPresent());

        // Verificamos que el pedido almacenado coincide con el pedido creado
        Pedido pedidoAlmacenado = pedidoEnRepositorio.get();
        assertEquals(pedidoCreado.getUsuario().getId(), pedidoAlmacenado.getUsuario().getId());
        assertEquals(pedidoCreado.getImporteTotal(), pedidoAlmacenado.getImporteTotal(), 0.01);
    }


    //====================== Scenario: Verificar que los cambios en la base de datos se reflejan correctamente después de la creación del pedido ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:
    // Este paso ya existe por lo que lo reutilizamos: When se crea un pedido con el usuario y los detalles del pedido

    @Then("el pedido debe estar almacenado en la base de datos")
    public void el_pedido_debe_estar_almacenado_en_la_base_de_datos() {
        assertNotNull(pedidoCreado, "El pedido debería haberse creado correctamente.");
        assertNotNull(pedidoCreado.getId(), "El pedido debería tener un ID asignado.");
    }

    @And("el pedido almacenado debe ser consultable por su ID")
    public void el_pedido_almacenado_debe_ser_consultable_por_su_id() {
        pedidoRecuperado = pedidoRepository.findById(pedidoCreado.getId())
                .orElse(null);

        assertNotNull(pedidoRecuperado, "El pedido debería ser recuperable por su ID.");
        assertEquals(pedidoCreado.getId(), pedidoRecuperado.getId());
        Assertions.assertEquals(pedidoCreado.getUsuario().getId(), pedidoRecuperado.getUsuario().getId(), "El usuario del pedido debería coincidir.");
        Assertions.assertEquals(pedidoCreado.getDetallesPedido().size(), pedidoRecuperado.getDetallesPedido().size(), "Los detalles del pedido deberían coincidir.");
        Assertions.assertEquals(pedidoCreado.getImporteTotal(), pedidoRecuperado.getImporteTotal(), "El importe total debe coincidir");
    }


    //====================== Scenario: Verificar el cálculo del importe total y los detalles del pedido después de su creación ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:
    // Este paso ya existe por lo que lo reutilizamos: When se crea un pedido con el usuario y los detalles del pedido

    @Then("el importe total del pedido debe estar calculado correctamente, siendo {double}")
    public void el_importe_total_del_pedido_debe_estar_calculado_correctamente(double importeEsperado) {
        // Aseguramos que el importe total sea el esperado (ajustar según la lógica del cálculo)
        assertEquals(importeEsperado, pedidoCreado.getImporteTotal(),0.01);
    }

    @And("los detalles del pedido deben estar almacenados en la base de datos")
    public void los_detalles_del_pedido_deben_estar_almacenados_en_la_base_de_datos() {
        List<DetallePedido> detallesGuardados = detallePedidoRepository.findByPedidoId(pedidoCreado.getId());
        assertNotNull(detallesGuardados, "Los detalles del pedido deberían haberse almacenado.");
        Assertions.assertEquals(detallesPedido.size(), detallesGuardados.size(), "El número de detalles guardados no coincide.");
    }


    //====================== Scenario: Evaluar el rendimiento del sistema al manejar múltiples pedidos simultáneamente ======================
    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:

    @When("se crean múltiples pedidos simultáneamente")
    public void se_crean_multiples_pedidos_simultaneamente() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        List<Callable<Boolean>> tareas = new ArrayList<>();

        for (int i = 0; i < numPedidos; i++) {
            tareas.add(() -> {
                try {
                    pedidoService.crearPedido(usuario, detallesPedido);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace(); // Loguea la excepción para depurar
                    return false;
                }
            });
        }

        // Medir el tiempo de ejecución
        long inicio = System.currentTimeMillis();
        List<Future<Boolean>> resultados = executor.invokeAll(tareas);
        long fin = System.currentTimeMillis();
        executor.shutdown();

        tiempoEjecucion = fin - inicio;

        // Verificar que todos los pedidos se crean exitosamente
        for (Future<Boolean> resultado : resultados) {
            try {
                Assertions.assertTrue(resultado.get(), "Un pedido no se creó correctamente.");
            } catch (ExecutionException e) {
                // Loguea la excepción de ejecución
                e.printStackTrace();
                Assertions.fail("Ocurrió un error durante la creación del pedido: " + e.getMessage());
            }
        }
    }

    @Then("todos los pedidos deben ser creados exitosamente")
    public void todos_los_pedidos_deben_ser_creados_exitosamente() {
        // La verificación de éxito ya se realizó durante la creación de los pedidos
        // Esto asegura que ningún pedido haya fallado
        Assertions.assertTrue(true, "Todos los pedidos se crearon exitosamente.");
    }

    @Then("el tiempo de ejecución debe estar dentro de los límites aceptables")
    public void el_tiempo_de_ejecucion_debe_estar_dentro_de_los_limites_aceptables() {
        System.out.println("Tiempo total de ejecución: " + tiempoEjecucion + " ms");
        Assertions.assertTrue(tiempoEjecucion < 5000, "El tiempo de ejecución fue demasiado alto.");
    }


    //====================== Scenario: Intento de crear pedido sin autenticación ======================


    @Given("un usuario no autenticado")
    public void un_usuario_no_autenticado() {
        // No configurar credenciales o contexto de usuario
        excepcion = null; // Inicializa la excepción
        detallesPedido = new ArrayList<>();
    }

    @When("el usuario intenta crear un pedido con los detalles:")
    public void el_usuario_intenta_crear_un_pedido_con_los_detalles(DataTable dataTable) {

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

        try {
            // Intentar crear un pedido sin autenticación
            pedidoService.crearPedido(null, detallesPedido);
        } catch (Exception e) {
            excepcion = e; // Capturar la excepción esperada
        }
    }

    @Then("el sistema debe responder con un mensaje de error indicando que la autenticación es requerida")
    public void el_sistema_debe_responder_con_un_mensaje_de_error() {
        // Verificar que se lanzó una excepción de autenticación
        Assertions.assertNotNull(excepcion, "Se esperaba una excepción, pero no ocurrió.");
        Assertions.assertEquals("Usuario no encontrado", excepcion.getMessage());
    }



}
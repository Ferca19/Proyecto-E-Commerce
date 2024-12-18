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
import programacion.ejemplo.service.PedidoService;
import programacion.ejemplo.service.ProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = EjemploApplicationPruebas.class)
public class DetallePedidoPruebasSistema {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    private Usuario usuario;
    private List<DetallePedidoDTO> detallesPedido;
    private Pedido pedidoCreado;
    private Pedido pedidoRecuperado;
    private int numPedidos = 100;
    private long tiempoEjecucion;
    private Exception excepcion;
    private List<DetallePedido> detallesAnteriores;
    private List<DetallePedido> detallesGuardados;
    private Exception exception;
    private int productoIdRestarStock;
    private int cantidadRestarStock;
    private int stockAnteriorProducto;
    private int posibleStock;




    //====================== Scenario: Creación de Pedido con Productos Válidos ======================

    @Given("un usuario con ID {int} existe #4")
    public void un_usuario_con_ID_existe(Integer id) {
        usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Juan Perez");
    }

    @Given("los detalles del pedido son: #4")
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

    @When("se crea un pedido con el usuario y los detalles del pedido #4")
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

    @Then("el pedido debe ser creado con los detalles asignados #4")
    public void el_pedido_debe_ser_creado_con_los_detalles_asignados() {
        assertNotNull(pedidoCreado);
        assertNotNull(pedidoCreado.getDetallesPedido());
        Assertions.assertEquals(detallesPedido.size(), pedidoCreado.getDetallesPedido().size());
    }

    @Then("todos los detalles del pedido deben estar asociados al mismo pedido #2")
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
        Assertions.assertTrue(todosAsociados, "No todos los últimos detalles están asociados al mismo pedido con ID: " + pedidoIdAsociado);

    }


    @Then("el detalle de cada pedido debe ser guardado en el repositorio correctamente #2")
    public void el_detalle_de_cada_pedido_debe_ser_guardado_en_el_repositorio_correctamente() {

        // Recuperar los detalles del pedido desde el repositorio
        detallesGuardados = detallePedidoRepository.findAll();

        // Verificar que el número de detalles guardados coincide con el número de detalles creados
        Assertions.assertEquals((detallesAnteriores.size()+detallesPedido.size()), detallesGuardados.size(), "El número de detalles guardados no coincide.");

        // Iterar y verificar cada detalle guardado
        for (DetallePedidoDTO detalleDTO : detallesPedido) {
            // Buscar el detalle en la base de datos por productoId y cantidad
            boolean encontrado = detallesGuardados.stream().anyMatch(detalleGuardado ->
                    detalleGuardado.getProducto().getId().equals(detalleDTO.getProductoId()) &&
                            detalleGuardado.getCantidad().equals(detalleDTO.getCantidad())
            );

            // Afirmar que cada detalle se encuentra correctamente guardado
            Assertions.assertTrue(encontrado, "El detalle con productoId: " + detalleDTO.getProductoId() +
                    " y cantidad: " + detalleDTO.getCantidad() + " no fue encontrado en el repositorio.");
        }

        System.out.println("Todos los detalles del pedido se guardaron correctamente en el repositorio.");
    }

    @Then("el importe total del pedido debe estar calculado correctamente, siendo {double} #3")
    public void el_importe_total_del_pedido_debe_estar_calculado_correctamente(double importeEsperado) {
        // Verificar que el importe total fue calculado y asignado
        Assertions.assertEquals(importeEsperado, pedidoCreado.getImporteTotal());
    }


    //====================== Scenario: Validar que los cambios en la base de datos se reflejan correctamente ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:
    // Este paso ya existe por lo que lo reutilizamos: When se crea un pedido con el usuario y los detalles del pedido

    @Then("el pedido debe estar correctamente almacenado en la base de datos")
    public void el_pedido_debe_estar_correctamente_almacenado_en_la_base_de_datos() {
        // Buscar el pedido en el repositorio
        Optional<Pedido> pedidoGuardado = pedidoRepository.findById(pedidoCreado.getId());

        // Validar que el pedido fue almacenado
        assertTrue("El pedido no se guardó correctamente en la base de datos.", pedidoGuardado.isPresent());
        assertEquals(pedidoCreado.getId(), pedidoGuardado.get().getId());
    }


    @Then("los detalles del pedido deben ser consultables desde la base de datos")
    public void los_detalles_del_pedido_deben_ser_consultables_desde_la_base_de_datos() {

        // Recuperar los detalles del pedido por el ID del pedido creado
        List<DetallePedido> detallesGuardados = detallePedidoRepository.findByPedidoId(pedidoCreado.getId());

        // Validar que el número de detalles coincide
        assertEquals(detallesPedido.size(), detallesGuardados.size());

        // Validar que cada detalle tiene los datos correctos
        for (int i = 0; i < detallesPedido.size(); i++) {
            DetallePedidoDTO detalleEnviado = detallesPedido.get(i);
            DetallePedido detalleGuardado = detallesGuardados.get(i);

            assertEquals(detalleEnviado.getProductoId(), detalleGuardado.getProducto().getId());
            assertEquals(detalleEnviado.getCantidad(), detalleGuardado.getCantidad());
        }
    }


    //====================== Scenario: Validar la integración del servicio con el repositorio ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:
    // Este paso ya existe por lo que lo reutilizamos: When se crea un pedido con el usuario y los detalles del pedido

    @Then("el pedido debe estar correctamente almacenado en el repositorio")
    public void el_pedido_debe_estar_correctamente_almacenado_en_el_repositorio() {
        // Consultar el pedido en el repositorio
        Optional<Pedido> pedidoGuardado = pedidoRepository.findById(pedidoCreado.getId());

        // Validar que el pedido fue almacenado
        assertTrue(pedidoGuardado.isPresent());
        assertEquals(pedidoCreado.getId(), pedidoGuardado.get().getId());
    }


    @Then("los detalles del pedido deben estar correctamente asociados al pedido en el repositorio")
    public void los_detalles_del_pedido_deben_estar_correctamente_asociados_al_pedido_en_el_repositorio() {
        // Consultar los detalles del pedido en el repositorio
        List<DetallePedido> detallesGuardados = detallePedidoRepository.findByPedidoId(pedidoCreado.getId());

        // Validar que el número de detalles coincide
        assertEquals(detallesPedido.size(), detallesGuardados.size());

        // Validar que cada detalle tiene los datos correctos y está asociado al pedido
        for (int i = 0; i < detallesPedido.size(); i++) {
            DetallePedidoDTO detalleEnviado = detallesPedido.get(i);
            DetallePedido detalleGuardado = detallesGuardados.get(i);

            assertEquals(detalleEnviado.getProductoId(), detalleGuardado.getProducto().getId());
            assertEquals(detalleEnviado.getCantidad(), detalleGuardado.getCantidad());
            assertEquals(pedidoCreado.getId(), detalleGuardado.getPedido().getId());
        }
    }

    //====================== Scenario: Validar el rendimiento al crear múltiples pedidos simultáneamente ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:


    @When("se crean múltiples pedidos simultáneamente #2")
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

    @Then("todos los pedidos deben ser creados exitosamente #2")
    public void todos_los_pedidos_deben_ser_creados_exitosamente() {
        // La verificación de éxito ya se realizó durante la creación de los pedidos
        // Esto asegura que ningún pedido haya fallado
        Assertions.assertTrue(true, "Todos los pedidos se crearon exitosamente.");
    }

    @Then("el tiempo de ejecución debe estar dentro de los límites aceptables #2")
    public void el_tiempo_de_ejecucion_debe_estar_dentro_de_los_limites_aceptables() {
        System.out.println("Tiempo total de ejecución: " + tiempoEjecucion + " ms");
        Assertions.assertTrue(tiempoEjecucion < 5000, "El tiempo de ejecución fue demasiado alto.");
    }


    //====================== Scenario: Confirmar la integridad de los datos almacenados después de múltiples operaciones ======================

    // Este paso ya existe por lo que lo reutilizamos: Given un usuario con ID 3 existe
    // Este paso ya existe por lo que lo reutilizamos: And los detalles del pedido son:
    // Este paso ya existe por lo que lo reutilizamos: When se crea un pedido con el usuario y los detalles del pedido

    @Then("los datos almacenados en la base de datos deben coincidir con los esperados")
    public void los_datos_almacenados_en_la_base_de_datos_deben_coincidir_con_los_esperados() {
        // Verificar que el pedido está almacenado
        Pedido pedidoGuardado = pedidoRepository.findById(pedidoCreado.getId()).orElse(null);
        assertNotNull(pedidoGuardado, "El pedido no se almacenó en la base de datos.");

        // Verificar los detalles del pedido
        List<DetallePedido> detallesGuardados = detallePedidoRepository.findByPedidoId(pedidoGuardado.getId());
        assertEquals(detallesPedido.size(), detallesGuardados.size());

        for (int i = 0; i < detallesPedido.size(); i++) {
            DetallePedidoDTO detalleEsperado = detallesPedido.get(i);
            DetallePedido detalleGuardado = detallesGuardados.get(i);

            assertEquals(detalleEsperado.getProductoId(), detalleGuardado.getProducto().getId());
            assertEquals(detalleEsperado.getCantidad(), detalleGuardado.getCantidad());
            assertEquals(
                    productoService.obtenerPorId(detalleEsperado.getProductoId()).getPrecio() * detalleEsperado.getCantidad(),
                    detalleGuardado.getSubtotal(),0.1);
        }

        // Validación final
        System.out.println("Integridad de datos verificada correctamente.");
    }



}
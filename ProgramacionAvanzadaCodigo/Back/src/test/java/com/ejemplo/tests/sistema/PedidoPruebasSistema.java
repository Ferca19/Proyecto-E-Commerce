package com.ejemplo.tests.sistema;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.EjemploApplicationPruebas;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.PedidoRepository;
import programacion.ejemplo.service.DetallePedidoService;
import programacion.ejemplo.service.EstadoService;
import programacion.ejemplo.service.PedidoService;
import programacion.ejemplo.service.ProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private EstadoService estadoService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    private Usuario usuario;
    private List<DetallePedidoDTO> detallesPedido;
    private Pedido pedidoCreado;

    @Given("un usuario con ID {int} y nombre {string} existe en el sistema")
    public void un_usuario_con_ID_y_nombre_existe_en_el_sistema(Integer id, String nombre) {
        usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(nombre);
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
        // Obtenemos el estado inicial desde el servicio
        Estado estadoPendiente = estadoService.obtenerEstadoInicial();

        // Creamos el pedido
        pedidoCreado = pedidoService.crearPedido(usuario, detallesPedido);
        pedidoCreado.setEstado(estadoPendiente);

        // Guardamos el pedido en el repositorio
        pedidoRepository.save(pedidoCreado);
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

    @Then("el importe total del pedido debe estar calculado correctamente")
    public void el_importe_total_del_pedido_debe_estar_calculado_correctamente() {
        // Aseguramos que el importe total sea el esperado (ajustar según la lógica del cálculo)
        assertEquals(140000.0, pedidoCreado.getImporteTotal(),0.01);
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

    private void assertNotNull(Pedido pedido) {
        if (pedido == null) {
            throw new AssertionError("El pedido es nulo");
        }
    }
}
package com.ejemplo.tests.integracion;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.EjemploApplicationPruebas;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.service.DetallePedidoService;
import programacion.ejemplo.service.EstadoService;
import programacion.ejemplo.service.PedidoService;
import programacion.ejemplo.service.UsuarioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(classes = EjemploApplicationPruebas.class)
public class PedidoPruebasIntegracion {

    @Autowired
    private PedidoService pedidoService;


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

    @Then("el importe total del pedido debe estar calculado correctamente")
    public void el_importe_total_del_pedido_debe_estar_calculado_correctamente() {
        // Verificar que el importe total fue calculado y asignado
        assertEquals(100000.0, pedidoCreado.getImporteTotal());
    }
}
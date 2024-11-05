package com.ejemplo.tests.unitarias;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.service.DetallePedidoService;
import programacion.ejemplo.service.EstadoService;
import programacion.ejemplo.service.PedidoService;
import programacion.ejemplo.service.UsuarioService;



@ExtendWith(MockitoExtension.class)
public class PedidoServicePruebasUnitarias {

    @InjectMocks
    private PedidoService pedidoService;

    private List<DetallePedidoDTO> detallesPedido;

    private Pedido pedidoCreado;
    private Integer usuarioId;

    @Given("un usuario con ID {int} existe")
    public void un_usuario_con_ID_existe(Integer id) {
        this.usuarioId = id;
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

    @When("se crea un pedido con el usuario ID y los detalles del pedido")
    public void se_crea_un_pedido_con_el_usuario_ID_y_los_detalles_del_pedido() {
        // Simula el estado inicial como un objeto de tipo Estado
        Estado estadoPendiente = new Estado();
        estadoPendiente.setNombre("Pendiente");


        // Llamar al método que estamos probando
        pedidoCreado = pedidoService.crearPedido(usuarioId, detallesPedido);
    }

    @Then("el pedido debe ser creado con estado inicial")
    public void el_pedido_debe_ser_creado_con_estado_inicial() {
        // Verificar que el pedido tiene el estado inicial asignado
        assertEquals("Pendiente", pedidoCreado.getEstado());
    }

    @Then("el importe total del pedido debe estar calculado")
    public void el_importe_total_del_pedido_debe_estar_calculado() {
        // Verificar que el importe total fue calculado y asignado
        assertEquals(100.0, pedidoCreado.getImporteTotal());
    }
}

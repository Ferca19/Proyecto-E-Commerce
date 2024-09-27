package programacion.ejemplo.service;

import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.model.Pedido;

import java.util.List;

public interface IDetallePedidoService {
    double crearDetallePedido(Pedido pedido, List<DetallePedidoDTO> detallesPedidoDTO);
}

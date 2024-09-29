package programacion.ejemplo.service;

import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.PedidoDTO;
import programacion.ejemplo.model.Pedido;

import java.util.List;

public interface IPedidoService {
    List<PedidoDTO> getAllPedidos();
    Pedido crearPedido(Integer usuarioId, List<DetallePedidoDTO> detallesPedidoDTO);

    void eliminarPedido(Integer pedidoId);

    void recuperarPedido(Integer pedidoId);

    boolean existePorEstadoId(Integer estadoId);
}

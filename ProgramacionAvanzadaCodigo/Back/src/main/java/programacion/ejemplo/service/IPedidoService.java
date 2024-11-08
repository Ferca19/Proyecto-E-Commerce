package programacion.ejemplo.service;

import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.PedidoDTO;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;

import java.util.List;

public interface IPedidoService {
    List<PedidoDTO> getAllPedidos();
    Pedido crearPedido(Usuario usuario, List<DetallePedidoDTO> detallesPedidoDTO);

    void eliminarPedido(Integer pedidoId);

    void recuperarPedido(Integer pedidoId);

    boolean existePorEstadoId(Integer estadoId);
}

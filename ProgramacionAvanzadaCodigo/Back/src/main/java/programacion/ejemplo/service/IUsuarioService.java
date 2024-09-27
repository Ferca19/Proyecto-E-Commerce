package programacion.ejemplo.service;

import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.PedidoDTO;
import programacion.ejemplo.DTO.RegisterDTO;
import programacion.ejemplo.DTO.UsuarioDTO;

import java.util.List;

public interface IUsuarioService {

    UsuarioDTO crearUsuario(RegisterDTO registerDTO);

    PedidoDTO crearPedido(Integer usuarioId, List<DetallePedidoDTO> detallesPedidoDTO);

    UsuarioDTO obtenerPorId(Integer usuarioId);
}

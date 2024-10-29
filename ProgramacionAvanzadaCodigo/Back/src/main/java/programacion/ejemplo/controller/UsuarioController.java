package programacion.ejemplo.controller;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.PedidoDTO;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.Mapper.PedidoMapper;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.UsuarioRepository;
import programacion.ejemplo.service.IUsuarioService;
import programacion.ejemplo.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("usuarios")
@CrossOrigin(value="http://localhost:5173")

public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;



    @PostMapping("public/{usuarioId}/registrarpedido")
    public ResponseEntity<PedidoDTO> crearPedido(@PathVariable Integer usuarioId, @RequestBody List<DetallePedidoDTO> detallesPedidoDTO) {


        if (detallesPedidoDTO == null || detallesPedidoDTO.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 si la lista está vacía
        }

        try {

            PedidoDTO nuevoPedidoDTO = usuarioService.crearPedido(usuarioId, detallesPedidoDTO);

            return new ResponseEntity<>(nuevoPedidoDTO, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Error al crear el pedido: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("admin/{usuarioId}/pedidos/{pedidoId}/eliminar")
    public ResponseEntity<String> eliminarPedido( @PathVariable Integer pedidoId) {
        try {
            usuarioService.eliminarPedido(pedidoId);
            return ResponseEntity.ok("Pedido eliminado exitosamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/{usuarioId}/pedidos/{pedidoId}/recuperar")
    public ResponseEntity<String> recuperarPedido( @PathVariable Integer pedidoId) {
        try {
            usuarioService.recuperarPedido(pedidoId);
            return ResponseEntity.ok("Pedido recuperado exitosamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

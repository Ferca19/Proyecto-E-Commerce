package programacion.ejemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import programacion.ejemplo.DTO.PedidoDTO;
import programacion.ejemplo.service.IPedidoService;

import java.util.List;

@RestController
@RequestMapping("pedidos")
@CrossOrigin(value=" http://localhost:5173")

public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private IPedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodosLosPedidos() {
        List<PedidoDTO> pedidos = pedidoService.getAllPedidos();
        return ResponseEntity.ok(pedidos);
    }
}

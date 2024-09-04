package programacion.ejemplo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.service.ProductoService;

@RestController
@RequestMapping("/productos")

public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody ProductoDTO productoDTO) {
        Producto nuevoProducto = productoService.createProducto(
                productoDTO.getNombre(),
                productoDTO.getPrecio(),
                productoDTO.getCategoriaId(),
                productoDTO.getMarcaId()
        );
        return ResponseEntity.ok(nuevoProducto);
    }
}

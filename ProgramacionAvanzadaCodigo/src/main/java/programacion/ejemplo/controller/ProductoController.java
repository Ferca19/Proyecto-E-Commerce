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
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO nuevoProducto = productoService.createProducto(productoDTO);
            return ResponseEntity.ok(nuevoProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Maneja el error adecuadamente
        }
    }
}

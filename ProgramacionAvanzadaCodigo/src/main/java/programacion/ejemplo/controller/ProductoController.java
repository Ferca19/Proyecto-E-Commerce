package programacion.ejemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.service.ICategoriaService;
import programacion.ejemplo.service.IProductoService;
import programacion.ejemplo.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/productos")

public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService modelService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar() {
        List<ProductoDTO> productos = modelService.listar();
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<?> createProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO nuevoProducto = modelService.createProducto(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (RuntimeException e) {
            // Agrega el mensaje de error en el cuerpo de la respuesta para facilitar la depuración
            return ResponseEntity.badRequest().body("Error al crear el producto: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Integer id) {
        logger.info("Obteniendo producto por ID: {}", id);
        ProductoDTO productoDTO = modelService.obtenerPorId(id);
        return ResponseEntity.ok(productoDTO);
    }
}

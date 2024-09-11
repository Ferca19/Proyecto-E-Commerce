package programacion.ejemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.ProductoVarianteDTO;
import programacion.ejemplo.service.ProductoVarianteService;

import java.util.List;

@RestController
@RequestMapping("/ejemplo")
@CrossOrigin("http://localhost:5173")
public class ProductoVarianteController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoVarianteController.class);

    @Autowired
    private ProductoVarianteService varianteService;

    @PostMapping("/variantes")
    public ResponseEntity<ProductoVarianteDTO> guardar(@RequestBody ProductoVarianteDTO dto) {
        try {
            ProductoVarianteDTO nuevaVariante = varianteService.guardar(dto, dto.getProductoIds());
            return ResponseEntity.ok(nuevaVariante);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoVarianteDTO>> listar() {
        logger.info("Listando todas las variantes de producto.");
        List<ProductoVarianteDTO> variantes = varianteService.listar();
        return ResponseEntity.ok(variantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoVarianteDTO> obtenerPorId(@PathVariable Integer id) {
        logger.info("Obteniendo variante por ID: {}", id);
        return ResponseEntity.ok(varianteService.obtenerPorId(id));
    }


    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ProductoVarianteDTO>> obtenerPorProductoId(@PathVariable Integer productoId) {
        logger.info("Obteniendo variantes para producto ID: {}", productoId);
        return ResponseEntity.ok(varianteService.obtenerPorProductoId(productoId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        logger.info("Eliminando variante ID: {}", id);
        varianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

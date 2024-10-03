package programacion.ejemplo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import programacion.ejemplo.DTO.ActualizarProductoDTO;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.service.IProductoService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(value="http://localhost:5173")

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
    public ResponseEntity<?> createProducto(
            @ModelAttribute ProductoDTO productoDTO,
            @RequestParam("file") MultipartFile file) {
        try {
            // Ruta donde se guardará la imagen
            String path = "D:\\USUARIO\\Desktop\\Proyecto-Programacion-Avanzada\\Imagenes\\";
            File imageFile = new File(path + file.getOriginalFilename());

            // Crear el directorio si no existe
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }

            // Guardar la imagen
            file.transferTo(imageFile);

            // Asignar la ruta de la imagen al DTO
            productoDTO.setImagen(imageFile.getAbsolutePath());

            // Crear el producto
            ProductoDTO nuevoProducto = modelService.createProducto(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el producto: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable Integer id,
            @RequestParam(value = "imagen", required = false) MultipartFile file,
            @RequestParam(value = "producto", required = false) String productoJson) { // Cambiado a String
        try {
            // Verifica que se haya enviado al menos una de las partes
            if (productoJson == null && (file == null || file.isEmpty())) {
                return ResponseEntity.badRequest().body("Se debe proporcionar al menos un campo para actualizar.");
            }

            ActualizarProductoDTO actualizarProductoDTO = null;

            // Si se proporcionó el JSON, conviértelo a DTO
            if (productoJson != null) {
                // Aquí debes tener un método para convertir el JSON a tu DTO
                actualizarProductoDTO = new ObjectMapper().readValue(productoJson, ActualizarProductoDTO.class);
            }

            // Si el DTO es nulo, solo actualiza la imagen
            if (actualizarProductoDTO == null) {
                Producto productoExistente = modelService.obtenerObjetoPorId(id);
                if (file != null && !file.isEmpty()) {
                    return ResponseEntity.ok(modelService.actualizarImagenProducto(productoExistente, file));
                } else {
                    return ResponseEntity.badRequest().body("Se debe proporcionar una imagen para actualizar.");
                }
            }

            // Si se proporciona el DTO, actualiza el producto normalmente
            Producto productoActualizado = modelService.actualizarProducto(id, actualizarProductoDTO, file);
            return ResponseEntity.ok(productoActualizado);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Integer id) {
        logger.info("Obteniendo producto por ID: {}", id);
        ProductoDTO productoDTO = modelService.obtenerPorId(id);
        return ResponseEntity.ok(productoDTO);
    }
}

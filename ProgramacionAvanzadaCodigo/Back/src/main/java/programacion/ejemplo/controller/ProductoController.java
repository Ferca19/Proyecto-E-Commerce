package programacion.ejemplo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import programacion.ejemplo.exception.RecursoNoEncontradoExcepcion;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.service.IProductoService;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/productos")
@CrossOrigin(value="http://localhost:5173")

public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService modelService;

    @Value("${app.images.base-path}")
    private String baseImagePath;

    @GetMapping("/public")
    public ResponseEntity<List<ProductoDTO>> listar() {
        List<ProductoDTO> productos = modelService.listar();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/public/imagenes/banner")
    public List<String> listarImagenesDeBanner() {
        try {
            // Usamos Files.walk para obtener todos los archivos en el directorio
            return Files.walk(Paths.get(baseImagePath), 1) // Profundidad 1 para evitar recorrer subdirectorios
                    .filter(Files::isRegularFile) // Filtra solo archivos
                    .map(Path::getFileName) // Obtiene solo el nombre del archivo
                    .map(Path::toString) // Convierte Path a String
                    .filter(nombre -> nombre.contains("banner")) // Filtra los archivos que contienen "banner"
                    .collect(Collectors.toList()); // Colecciona los nombres de archivos en una lista
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Devuelve una lista vacía si ocurre un error
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createProducto(
            @RequestParam("imagen") MultipartFile file,
            @RequestParam(value = "producto", required = true) String productoJson) {
        try {
            // Convertir el JSON a ProductoDTO
            ObjectMapper objectMapper = new ObjectMapper();
            ProductoDTO productoDTO = objectMapper.readValue(productoJson, ProductoDTO.class);

            // Construir la ruta completa de la imagen usando la ruta base configurada
            File imageFile = new File(baseImagePath + file.getOriginalFilename());

            // Crear el directorio si no existe
            if (!new File(baseImagePath).exists()) {
                new File(baseImagePath).mkdirs();
            }

            // Verificar si la imagen ya existe
            if (imageFile.exists()) {
                // La imagen ya existe, asignar solo el nombre del archivo al DTO
                productoDTO.setImagen(file.getOriginalFilename());
            } else {
                // Si no existe, guardar la nueva imagen
                file.transferTo(imageFile);
                // Asignar solo el nombre del archivo (ruta relativa) al DTO
                productoDTO.setImagen(file.getOriginalFilename());
            }

            // Crear el producto
            ProductoDTO nuevoProducto = modelService.createProducto(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el producto: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen: " + e.getMessage());
        }
    }

    @PutMapping("/admin/{id}")
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
                try {
                    actualizarProductoDTO = new ObjectMapper().readValue(productoJson, ActualizarProductoDTO.class);
                } catch (JsonProcessingException e) {
                    return ResponseEntity.badRequest().body("JSON de producto no válido.");
                }
            }

            Producto productoExistente = modelService.obtenerObjetoPorId(id);
            if (productoExistente == null) {
                return ResponseEntity.notFound().build();
            }

            // Actualizar producto y/o imagen según corresponda
            if (actualizarProductoDTO != null) {
                // Actualiza los datos del producto
                Producto productoActualizado = modelService.actualizarProducto(id, actualizarProductoDTO);
                // Si hay una imagen, actualizarla
                if (file != null && !file.isEmpty()) {
                    modelService.actualizarImagenProducto(productoActualizado, file);
                }
                return ResponseEntity.ok(productoActualizado);
            } else {
                // Solo actualizar la imagen si no se proporcionó un DTO
                if (file != null && !file.isEmpty()) {
                    modelService.actualizarImagenProducto(productoExistente, file);
                    return ResponseEntity.ok(productoExistente);
                } else {
                    return ResponseEntity.badRequest().body("Se debe proporcionar una imagen para actualizar.");
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }


    @GetMapping("/public/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Integer id) {
        logger.info("Obteniendo producto por ID: {}", id);
        ProductoDTO productoDTO = modelService.obtenerPorId(id);
        return ResponseEntity.ok(productoDTO);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        ProductoDTO model = modelService.obtenerPorId(id);
        if (model == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }

        modelService.eliminarProducto(id);

        return ResponseEntity.ok().build();
    }
}

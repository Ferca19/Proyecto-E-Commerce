package programacion.ejemplo.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import programacion.ejemplo.DTO.ActualizarProductoDTO;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.Mapper.CategoriaMapper;
import programacion.ejemplo.Mapper.ProductoMapper;
import programacion.ejemplo.model.*;
import programacion.ejemplo.repository.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository modelRepository;

    @Autowired
    @Lazy
    private ICategoriaService categoriaService;

    @Autowired
    @Lazy
    private ISubcategoriaService subcategoriaService;

    @Autowired
    @Lazy
    private IMarcaService marcaService;

    @Autowired
    private ProductoMapper productoMapper;

    @Value("${app.images.base-path}")  // Inyecta el path desde application.properties
    private String baseImagePath;


    @Transactional
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        logger.info("Creando producto: {}", productoDTO);

        if (productoDTO.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser 0 o negativo");
        }

        Categoria categoria = categoriaService.obtenerPorId(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Subcategoria subcategoria = subcategoriaService.obtenerPorId(productoDTO.getSubcategoriaId())
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        Marca marca = marcaService.obtenerPorId(productoDTO.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        Producto producto = productoMapper.toEntity(productoDTO, categoria, subcategoria, marca);

        logger.info("Guardando producto: {}", producto);

        Producto nuevoProducto = modelRepository.save(producto);
        logger.info("Producto guardado exitosamente con ID: {}", nuevoProducto.getId());

        return productoMapper.toDto(nuevoProducto);
    }

    @Override
    public Producto actualizarProducto(Integer id, ActualizarProductoDTO actualizarProductoDTO) throws IOException {
        Producto productoExistente = modelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Actualizar los campos si están presentes en el DTO
        if (actualizarProductoDTO.getNombre() != null) productoExistente.setNombre(actualizarProductoDTO.getNombre());
        if (actualizarProductoDTO.getDescripcion() != null) productoExistente.setDescripcion(actualizarProductoDTO.getDescripcion());
        if (actualizarProductoDTO.getPrecio() != null) productoExistente.setPrecio(actualizarProductoDTO.getPrecio());
        if (actualizarProductoDTO.getTamano() != null) productoExistente.setTamano(actualizarProductoDTO.getTamano());
        if (actualizarProductoDTO.getColor() != null) productoExistente.setColor(actualizarProductoDTO.getColor());
        if (actualizarProductoDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaService.buscarPorId(actualizarProductoDTO.getCategoriaId());
            if (categoria == null) {
                throw new RuntimeException("Categoría no encontrada");
            }
            productoExistente.setCategoria(categoria);
        }

        if (actualizarProductoDTO.getSubcategoriaId() != null) {
            Subcategoria subcategoria = subcategoriaService.buscarPorId(actualizarProductoDTO.getSubcategoriaId());
            if (subcategoria == null) {
                throw new RuntimeException("Subcategoría no encontrada");
            }
            productoExistente.setSubcategoria(subcategoria);
        }

        if (actualizarProductoDTO.getMarcaId() != null) {
            Marca marca = marcaService.buscarPorId(actualizarProductoDTO.getMarcaId());
            if (marca == null) {
                throw new RuntimeException("Marca no encontrada");
            }
            productoExistente.setMarca(marca);
        }

        // Guardar cambios en la base de datos
        return modelRepository.save(productoExistente);
    }

    public Producto actualizarImagenProducto(Producto producto, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            // Construir la ruta completa para la imagen
            String rutaImagen = baseImagePath + file.getOriginalFilename();
            File imageFile = new File(rutaImagen);

            // Verificar si la imagen ya existe
            if (imageFile.exists()) {
                // La imagen ya existe, actualiza solo el producto
                producto.setImagen(file.getOriginalFilename());
                // Guardar cambios en la base de datos
                return modelRepository.save(producto);
            } else {
                // Si no existe, guardar la nueva imagen
                file.transferTo(imageFile);
                producto.setImagen(file.getOriginalFilename());
                // Guardar cambios en la base de datos
                return modelRepository.save(producto);
            }
        } else {
            throw new RuntimeException("El archivo de imagen no puede estar vacío.");
        }
    }


    @Override
    public ProductoDTO obtenerPorId(Integer id) {
        Producto producto = modelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        return productoMapper.toDto(producto);
    }

    @Override
    public Producto obtenerObjetoPorId(Integer id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    @Override
    public List<ProductoDTO> listar() {
        List<Producto> productos = modelRepository.findAllByEliminado(0);
        return productos.stream()
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public ProductoDTO actualizarStockProducto(Producto producto) {
        // Verificar que la categoría no sea nula
        if (producto.getCategoria() == null) {
            throw new IllegalArgumentException("La categoría no puede ser nula");
        }

        if (producto.getSubcategoria() == null) {
            throw new IllegalArgumentException("La subcategoría no puede ser nula");
        }

        if (producto.getMarca() == null) {
            throw new IllegalArgumentException("La marca no puede ser nula");
        }

        Producto productoActualizado = modelRepository.save(producto);
        return productoMapper.toDto(productoActualizado);
    }

    @Override
    public void eliminarProducto(Integer productoId) {


        Producto producto = modelRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));


        if (producto.getEliminado() == Producto.SI) {
            throw new RuntimeException("El producto ya está eliminado.");
        }

        producto.asEliminar();

        modelRepository.save(producto);
    }

    @Override
    public Categoria obtenerCategoria(Integer categoriaId) {
        return categoriaService.buscarPorId(categoriaId);
    }

    @Override
    public Subcategoria obtenerSubcategoria(Integer subcategoriaId) {
        return subcategoriaService.buscarPorId(subcategoriaId);
    }

    @Override
    public Marca obtenerMarca(Integer marcaId) {
        return marcaService.buscarPorId(marcaId);
    }

    @Override
    public boolean existePorCategoriaId(Integer categoriaId) {
        return modelRepository.existsByCategoriaId(categoriaId);
    }

    @Override
    public boolean existePorMarcaId(Integer marcaId) {
        return modelRepository.existsByMarcaId(marcaId);
    }

    @Override
    public boolean existePorSubcategoriaId(Integer subcategoriaId) {
        return modelRepository.existsBySubcategoriaId(subcategoriaId);
    }

    @Override
    public List<Producto> findAllById(List<Integer> ids) {
        return modelRepository.findAllById(ids);
    }

    // Método para ajustar el inventario de un producto específico

    public void ajustarInventarioProducto(Integer productoId, int cantidadAjustada, int tipoAjuste) {

        Producto producto = obtenerObjetoPorId(productoId); // Obtener el producto por ID

        // Verificar si el producto existe
        if (producto == null) {
            throw new IllegalArgumentException("El producto con ID " + productoId + " no existe.");
        }

        // Verificar que la cantidad ajustada sea válida
        if (cantidadAjustada <= 0) {
            throw new IllegalArgumentException("La cantidad ajustada debe ser mayor que cero.");
        }

        if (tipoAjuste == 1) {
            // Lógica para reducir el inventario

            if (producto.getStock() < cantidadAjustada) {
                throw new IllegalArgumentException("No se puede reducir el stock. Stock actual: " + producto.getStock() + ", cantidad a ajustar: " + cantidadAjustada);
            }
            producto.setStock(producto.getStock() - cantidadAjustada);

        } else if (tipoAjuste == 2) {

            // Lógica para aumentar el inventario
            producto.setStock(producto.getStock() + cantidadAjustada);

        } else {
            throw new IllegalArgumentException("Tipo de ajuste inválido. Debe ser 1 (reducción) o 2 (aumento).");
        }

        // Guarda los cambios en el producto (suponiendo que hay un repositorio de productos)
        modelRepository.save(producto);
    }

}

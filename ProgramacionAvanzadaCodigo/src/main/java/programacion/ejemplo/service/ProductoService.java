package programacion.ejemplo.service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.Mapper.ProductoMapper;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Marca;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.repository.CategoriaRepository;
import programacion.ejemplo.repository.MarcaRepository;
import programacion.ejemplo.repository.ProductoRepository;
import programacion.ejemplo.model.ProductoVariante;
import programacion.ejemplo.repository.ProductoVarianteRepository;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoVarianteRepository productoVarianteRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Transactional
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        // Buscar la categoría y marca por sus IDs
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Marca marca = marcaRepository.findById(productoDTO.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        // Convertir el DTO a entidad Producto
        Producto producto = productoMapper.toEntity(productoDTO, categoria, marca);

        // Asignar las variantes al producto si existen
        if (productoDTO.getVariantes() != null) {
            List<ProductoVariante> variantes = productoDTO.getVariantes().stream()
                    .map(v -> {
                        // Recuperar variante por ID, o lanzar excepción si no existe
                        ProductoVariante variante = productoVarianteRepository.findById(v.getId())
                                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
                        return variante;
                    }).collect(Collectors.toList());

            // Asociar las variantes con el producto
            producto.setVariantes(variantes);

            // Actualizar la relación en las variantes
            for (ProductoVariante variante : variantes) {
                if (!variante.getProductos().contains(producto)) {
                    variante.getProductos().add(producto);
                    productoVarianteRepository.save(variante); // Guardar la variante con la asociación actualizada
                }
            }
        } else {
            // Si no hay variantes, inicializar la lista como vacía
            producto.setVariantes(new ArrayList<>());
        }

        // Guardar el producto
        Producto nuevoProducto = productoRepository.save(producto);

        // Retornar el producto convertido a DTO
        return productoMapper.toDto(nuevoProducto);
    }

    @Override
    public ProductoDTO updateProducto(Integer id, ProductoDTO productoDTO) {
        return null;
    }

    @Override
    public ProductoDTO obtenerPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoMapper.toDto(producto);
    }

    @Override
    public List<ProductoDTO> getAllProductos() {
        return List.of();
    }

    @Override
    public void deleteProducto(Integer id) {

    }
}
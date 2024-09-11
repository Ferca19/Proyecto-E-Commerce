package programacion.ejemplo.service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.Mapper.CategoriaMapper;
import programacion.ejemplo.Mapper.ProductoMapper;
import programacion.ejemplo.model.*;
import programacion.ejemplo.repository.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository modelRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoVarianteRepository productoVarianteRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private SubcategoriaRepository subcategoriaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Transactional
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        // Buscar la categoría y marca por sus IDs
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Subcategoria subcategoria = subcategoriaRepository.findById(productoDTO.getSubcategoriaId())
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        Marca marca = marcaRepository.findById(productoDTO.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        // Convertir el DTO a entidad Producto
        Producto producto = productoMapper.toEntity(productoDTO, categoria,subcategoria, marca);

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
                // Verifica si el producto ya está en la lista de productos de la variante
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
        Producto nuevoProducto = modelRepository.save(producto);

        // Retornar el producto convertido a DTO
        return productoMapper.toDto(nuevoProducto);
    }


    @Override
    public ProductoDTO updateProducto(Integer id, ProductoDTO productoDTO) {
        return null;
    }

    @Override
    public ProductoDTO obtenerPorId(Integer id) {
        Producto producto = modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoMapper.toDto(producto);
    }

    @Override
    public List<ProductoDTO> listar() {
        List<Producto> productos = modelRepository.findAllByEliminado(0);
        return productos.stream()
                .map(productoMapper::toDto) // Usar el método de instancia toDto del mapper
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProducto(Integer id) {

    }
}
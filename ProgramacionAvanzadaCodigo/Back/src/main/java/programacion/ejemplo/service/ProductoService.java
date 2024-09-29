package programacion.ejemplo.service;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.Mapper.ProductoMapper;
import programacion.ejemplo.model.*;
import programacion.ejemplo.repository.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {

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

    @Transactional
    public ProductoDTO createProducto(ProductoDTO productoDTO) {

        if (productoDTO.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo");
        }

        Categoria categoria = categoriaService.obtenerPorId(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Subcategoria subcategoria = subcategoriaService.obtenerPorId(productoDTO.getSubcategoriaId())
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        Marca marca = marcaService.obtenerPorId(productoDTO.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        Producto producto = productoMapper.toEntity(productoDTO, categoria, subcategoria, marca);

        Producto nuevoProducto = modelRepository.save(producto);

        return productoMapper.toDto(nuevoProducto);
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

}

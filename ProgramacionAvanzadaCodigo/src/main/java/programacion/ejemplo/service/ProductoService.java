package programacion.ejemplo.service;

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

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        // Buscar la categoría y marca por sus IDs
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Marca marca = marcaRepository.findById(productoDTO.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        // Convertir el DTO a entidad Producto
        Producto producto = productoMapper.toEntity(productoDTO, categoria, marca);

        // Guardar y retornar el producto convertido a DTO
        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public ProductoDTO updateProducto(Integer id, ProductoDTO productoDTO) {
        return null;
    }

    @Override
    public ProductoDTO getProductoById(Integer id) {
        return null;
    }

    @Override
    public List<ProductoDTO> getAllProductos() {
        return List.of();
    }

    @Override
    public void deleteProducto(Integer id) {

    }
}
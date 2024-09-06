package programacion.ejemplo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.ejemplo.DTO.ProductoVarianteDTO;
import programacion.ejemplo.Mapper.ProductoVarianteMapper;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.model.ProductoVariante;
import programacion.ejemplo.repository.ProductoRepository;
import programacion.ejemplo.repository.ProductoVarianteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ProductoVarianteService implements IProductoVariante {

    private static final Logger logger = LoggerFactory.getLogger(ProductoVarianteService.class);

    @Autowired
    private ProductoVarianteRepository productoVarianteRepository;

    @Autowired
    private ProductoRepository productoRepository; // Inyecta el repositorio del producto


    @Override
    public List<ProductoVarianteDTO> listar() {
        List<ProductoVariante> variantes = productoVarianteRepository.findAll();
        return variantes.stream().map(ProductoVarianteMapper::toDTO).toList();
    }

    @Override
    public ProductoVarianteDTO obtenerPorId(Integer id) {
        ProductoVariante variante = productoVarianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        return ProductoVarianteMapper.toDTO(variante);
    }

    @Override
    public ProductoVarianteDTO guardar(ProductoVarianteDTO productoVarianteDTO, Integer productoId) {
        // Buscar el producto por ID
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Mapear DTO a entidad
        ProductoVariante productoVariante = ProductoVarianteMapper.toEntity(productoVarianteDTO);

        // Asociar el producto con la variante
        productoVariante.setProducto(producto);

        // Guardar la variante
        return ProductoVarianteMapper.toDTO(productoVarianteRepository.save(productoVariante));
    }

    @Override
    public void eliminar(Integer id) {
        ProductoVariante productoVariante = productoVarianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        productoVarianteRepository.delete(productoVariante);
    }

    @Override
    public List<ProductoVarianteDTO> obtenerPorProductoId(Integer productoId) {
        List<ProductoVariante> variantes = productoVarianteRepository.findByProductoId(productoId);
        return variantes.stream().map(ProductoVarianteMapper::toDTO).toList();
    }
}

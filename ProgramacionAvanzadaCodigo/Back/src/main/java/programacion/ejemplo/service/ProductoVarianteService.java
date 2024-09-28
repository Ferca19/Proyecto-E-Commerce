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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoVarianteService implements IProductoVarianteService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoVarianteService.class);

    @Autowired
    private ProductoVarianteRepository modelRepository;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ProductoVarianteMapper productoVarianteMapper;

    @Override
    public List<ProductoVarianteDTO> listar() {
        List<ProductoVariante> variantes = modelRepository.findAll();
        return variantes.stream()
                .map(productoVarianteMapper::toDto)
                .toList();
    }

    @Override
    public Optional<ProductoVariante> obtenerPorId(Integer id) {
        return modelRepository.findById(id);
    }

    @Transactional
    public ProductoVarianteDTO guardar(ProductoVarianteDTO dto, List<Integer> productoIds) {
        ProductoVariante productoVariante = productoVarianteMapper.toEntity(dto);

        if (productoIds != null && !productoIds.isEmpty()) {
            List<Producto> productos = productoService.findAllById(productoIds);
            productoVariante.setProductos(productos);
        } else {
            productoVariante.setProductos(new ArrayList<>());
        }

        ProductoVariante guardada = modelRepository.save(productoVariante);
        return productoVarianteMapper.toDto(guardada);
    }

    @Override
    public void eliminar(Integer id) {
        ProductoVariante productoVariante = modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        modelRepository.delete(productoVariante);
    }

    @Override
    public List<ProductoVarianteDTO> obtenerPorProductoId(Integer productoId) {
        List<ProductoVariante> variantes = modelRepository.findByProductos_Id(productoId);
        return variantes.stream()
                .map(productoVarianteMapper::toDto)
                .toList();
    }

    @Override
    public ProductoVariante save(ProductoVariante variante) {
        return modelRepository.save(variante);
    }
}

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

@Service
public class ProductoVarianteService implements IProductoVariante {

    private static final Logger logger = LoggerFactory.getLogger(ProductoVarianteService.class);

    @Autowired
    private ProductoVarianteRepository productoVarianteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoVarianteMapper productoVarianteMapper;

    @Override
    public List<ProductoVarianteDTO> listar() {
        List<ProductoVariante> variantes = productoVarianteRepository.findAll();
        return variantes.stream()
                .map(productoVarianteMapper::toDto)
                .toList();
    }

    @Override
    public ProductoVarianteDTO obtenerPorId(Integer id) {
        ProductoVariante variante = productoVarianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        return productoVarianteMapper.toDto(variante);
    }

    @Transactional
    public ProductoVarianteDTO guardar(ProductoVarianteDTO dto, List<Integer> productoIds) {
        ProductoVariante productoVariante = productoVarianteMapper.toEntity(dto);

        if (productoIds != null && !productoIds.isEmpty()) {
            List<Producto> productos = productoRepository.findAllById(productoIds);
            productoVariante.setProductos(productos);
        } else {
            productoVariante.setProductos(new ArrayList<>());
        }

        ProductoVariante guardada = productoVarianteRepository.save(productoVariante);
        return productoVarianteMapper.toDto(guardada);
    }

    @Override
    public void eliminar(Integer id) {
        ProductoVariante productoVariante = productoVarianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        productoVarianteRepository.delete(productoVariante);
    }

    @Override
    public List<ProductoVarianteDTO> obtenerPorProductoId(Integer productoId) {
        List<ProductoVariante> variantes = productoVarianteRepository.findByProductos_Id(productoId);
        return variantes.stream()
                .map(productoVarianteMapper::toDto)
                .toList();
    }
}

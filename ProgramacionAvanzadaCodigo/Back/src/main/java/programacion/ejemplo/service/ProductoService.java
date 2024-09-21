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

        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Subcategoria subcategoria = subcategoriaRepository.findById(productoDTO.getSubcategoriaId())
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        Marca marca = marcaRepository.findById(productoDTO.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        Producto producto = productoMapper.toEntity(productoDTO, categoria,subcategoria, marca);

        if (productoDTO.getVariantes() != null) {
            List<ProductoVariante> variantes = productoDTO.getVariantes().stream()
                    .map(v -> {
                        ProductoVariante variante = productoVarianteRepository.findById(v.getId())
                                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
                        return variante;
                    }).collect(Collectors.toList());

            producto.setVariantes(variantes);

            for (ProductoVariante variante : variantes) {

                if (!variante.getProductos().contains(producto)) {
                    variante.getProductos().add(producto);
                    productoVarianteRepository.save(variante);
                }
            }
        } else {
            producto.setVariantes(new ArrayList<>());
        }

        Producto nuevoProducto = modelRepository.save(producto);

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
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProducto(Integer id) {

    }
}
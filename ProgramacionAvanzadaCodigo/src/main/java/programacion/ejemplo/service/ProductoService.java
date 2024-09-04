package programacion.ejemplo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.ejemplo.DTO.ProductoDTO;
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

    @Override
    public Producto createProducto(String nombre, double precio, Integer categoriaId, Integer marcaId) {
        // Buscar la categoría por su ID
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        // Buscar la marca por su ID
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        // Crear el nuevo producto
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCategoria(categoria);
        producto.setMarca(marca);

        // Guardar y retornar el producto
        return productoRepository.save(producto);
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
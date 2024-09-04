package programacion.ejemplo.service;

import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.model.Producto;

import java.util.List;

public interface IProductoService {
    ProductoDTO createProducto(ProductoDTO productoDTO);

    ProductoDTO updateProducto(Integer id, ProductoDTO productoDTO);

    ProductoDTO getProductoById(Integer id);

    List<ProductoDTO> getAllProductos();

    void deleteProducto(Integer id);
}

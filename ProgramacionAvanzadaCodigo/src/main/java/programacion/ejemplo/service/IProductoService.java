package programacion.ejemplo.service;

import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.model.Producto;

import java.util.List;

public interface IProductoService {
    Producto createProducto(String nombre, double precio, Integer categoriaId, Integer marcaId);

    ProductoDTO updateProducto(Integer id, ProductoDTO productoDTO);

    ProductoDTO getProductoById(Integer id);

    List<ProductoDTO> getAllProductos();

    void deleteProducto(Integer id);
}

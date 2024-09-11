package programacion.ejemplo.service;

import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.model.Producto;

import java.util.List;

public interface IProductoService {

    List<ProductoDTO> listar();

    ProductoDTO createProducto(ProductoDTO productoDTO);

    ProductoDTO updateProducto(Integer id, ProductoDTO productoDTO);

    ProductoDTO obtenerPorId(Integer id);


    void deleteProducto(Integer id);
}

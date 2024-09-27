package programacion.ejemplo.service;

import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Marca;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.model.Subcategoria;

import java.util.List;

public interface IProductoService {

    List<ProductoDTO> listar();

    ProductoDTO createProducto(ProductoDTO productoDTO);

    ProductoDTO updateProducto(Integer id, ProductoDTO productoDTO);

    ProductoDTO obtenerPorId(Integer id);


    void deleteProducto(Integer id);

    ProductoDTO actualizarProducto(Producto producto);

    Categoria obtenerCategoria(Integer categoriaId);

    Subcategoria obtenerSubcategoria(Integer subcategoriaId);

    Marca obtenerMarca(Integer marcaId);
}

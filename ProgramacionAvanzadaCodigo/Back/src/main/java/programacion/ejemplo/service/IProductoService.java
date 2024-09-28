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

    List<Producto> findAllById(List<Integer> ids);

    ProductoDTO createProducto(ProductoDTO productoDTO);

    ProductoDTO obtenerPorId(Integer id);

    ProductoDTO actualizarProducto(Producto producto);

    Categoria obtenerCategoria(Integer categoriaId);

    Subcategoria obtenerSubcategoria(Integer subcategoriaId);

    Marca obtenerMarca(Integer marcaId);

    boolean existePorCategoriaId(Integer categoriaId);
    boolean existePorMarcaId(Integer marcaId);
    boolean existePorSubcategoriaId(Integer subcategoriaId);
}

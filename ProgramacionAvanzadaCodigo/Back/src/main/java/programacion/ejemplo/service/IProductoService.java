package programacion.ejemplo.service;

import org.springframework.web.multipart.MultipartFile;
import programacion.ejemplo.DTO.ActualizarProductoDTO;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Marca;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.model.Subcategoria;

import java.io.IOException;
import java.util.List;

public interface IProductoService {

    List<ProductoDTO> listar();

    List<Producto> findAllById(List<Integer> ids);

    ProductoDTO createProducto(ProductoDTO productoDTO);

    ProductoDTO obtenerPorId(Integer id);

    void eliminarProducto(Integer productoId);

    Producto obtenerObjetoPorId(Integer id);

    ProductoDTO actualizarStockProducto(Producto producto);

    Categoria obtenerCategoria(Integer categoriaId);

    Subcategoria obtenerSubcategoria(Integer subcategoriaId);

    Marca obtenerMarca(Integer marcaId);

    Producto actualizarProducto(Integer id, ActualizarProductoDTO actualizarProductoDTO) throws IOException;

    Producto actualizarImagenProducto(Producto producto, MultipartFile file) throws IOException;

    boolean existePorCategoriaId(Integer categoriaId);
    boolean existePorMarcaId(Integer marcaId);
    boolean existePorSubcategoriaId(Integer subcategoriaId);
}

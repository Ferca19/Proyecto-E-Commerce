package programacion.ejemplo.service;

import programacion.ejemplo.DTO.ProductoVarianteDTO;
import programacion.ejemplo.model.ProductoVariante;

import java.util.List;

public interface IProductoVariante {

    List<ProductoVarianteDTO> listar();

    ProductoVarianteDTO obtenerPorId(Integer id);

    ProductoVarianteDTO guardar(ProductoVarianteDTO productoVarianteDTO, Integer productoId);

    void eliminar(Integer id);

    List<ProductoVarianteDTO> obtenerPorProductoId(Integer productoId);
}

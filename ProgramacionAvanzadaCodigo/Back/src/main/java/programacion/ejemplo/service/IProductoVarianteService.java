package programacion.ejemplo.service;

import programacion.ejemplo.DTO.ProductoVarianteDTO;
import programacion.ejemplo.model.ProductoVariante;

import java.util.List;
import java.util.Optional;

public interface IProductoVarianteService {

    List<ProductoVarianteDTO> listar();

    Optional<ProductoVariante> obtenerPorId(Integer id);

    ProductoVarianteDTO guardar(ProductoVarianteDTO dto, List<Integer> productoIds);

    void eliminar(Integer id);

    List<ProductoVarianteDTO> obtenerPorProductoId(Integer productoId);

    ProductoVariante save(ProductoVariante variante);
}

package programacion.ejemplo.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import programacion.ejemplo.DTO.ProductoVarianteDTO;
import programacion.ejemplo.model.ProductoVariante;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductoVarianteMapper {

    @Autowired
    private ProductoRepository productoRepository;

    public ProductoVarianteDTO toDto(ProductoVariante variante) {
        ProductoVarianteDTO dto = new ProductoVarianteDTO();
        dto.setId(variante.getId());
        dto.setNombreVariante(variante.getNombreVariante());
        dto.setValorVariante(variante.getValorVariante());
        if (variante.getProductos() != null) {
            dto.setProductoIds(variante.getProductos().stream()
                    .map(Producto::getId)
                    .collect(Collectors.toList()));
        } else {
            dto.setProductoIds(new ArrayList<>());
        }
        return dto;
    }

    public ProductoVariante toEntity(ProductoVarianteDTO dto) {
        ProductoVariante variante = new ProductoVariante();
        variante.setId(dto.getId());
        variante.setNombreVariante(dto.getNombreVariante());
        variante.setValorVariante(dto.getValorVariante());

        if (dto.getProductoIds() != null) {
            List<Producto> productos = dto.getProductoIds().stream()
                    .map(id -> productoRepository.findById(id).orElse(null))
                    .filter(producto -> producto != null)
                    .collect(Collectors.toList());
            variante.setProductos(productos);
        } else {
            variante.setProductos(new ArrayList<>());
        }

        return variante;
    }
}

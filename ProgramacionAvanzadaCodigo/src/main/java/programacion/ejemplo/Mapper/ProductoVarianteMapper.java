package programacion.ejemplo.Mapper;

import org.springframework.stereotype.Component;
import programacion.ejemplo.DTO.ProductoVarianteDTO;
import programacion.ejemplo.model.ProductoVariante;

@Component
public class ProductoVarianteMapper {

    public static ProductoVarianteDTO toDTO(ProductoVariante variante) {
        ProductoVarianteDTO dto = new ProductoVarianteDTO();
        dto.setId(variante.getId());
        dto.setNombreVariante(variante.getNombreVariante());
        dto.setValorVariante(variante.getValorVariante());
        dto.setProductoId(variante.getProducto().getId());
        return dto;
    }

    public static ProductoVariante toEntity(ProductoVarianteDTO dto) {
        ProductoVariante variante = new ProductoVariante();
        variante.setId(dto.getId());
        variante.setNombreVariante(dto.getNombreVariante());
        variante.setValorVariante(dto.getValorVariante());
        return variante;
    }
}
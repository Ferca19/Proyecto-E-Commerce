package programacion.ejemplo.Mapper;


import org.springframework.stereotype.Component;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.model.*;

@Component
public class ProductoMapper {


    // Convierte de entidad a DTO
    public ProductoDTO toDto(Producto model) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());
        dto.setPrecio(model.getPrecio());
        dto.setStock(model.getStock());
        dto.setEliminado(model.getEliminado());

        if (model.getCategoria() != null) {
            dto.setCategoriaId(model.getCategoria().getId());
        }
        if (model.getSubcategoria() != null) {
            dto.setSubcategoriaId(model.getSubcategoria().getId());
        }
        if (model.getMarca() != null) {
            dto.setMarcaId(model.getMarca().getId());
        }

        return dto;
    }

    public Producto toEntity(ProductoDTO dto, Categoria categoria, Subcategoria subcategoria, Marca marca) {
        Producto model = new Producto();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setDescripcion(dto.getDescripcion());
        model.setPrecio(dto.getPrecio());
        model.setStock(dto.getStock());
        model.setCategoria(categoria);
        model.setSubcategoria(subcategoria);
        model.setMarca(marca);
        model.setEliminado(dto.getEliminado());
        return model;
    }
}

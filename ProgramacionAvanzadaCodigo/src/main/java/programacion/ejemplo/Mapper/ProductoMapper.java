package programacion.ejemplo.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.DTO.ProductoVarianteDTO;
import programacion.ejemplo.model.*;

import java.util.stream.Collectors;

@Component
public class ProductoMapper {

    @Autowired
    private ProductoVarianteMapper productoVarianteMapper;

    // Convierte de entidad a DTO
    public ProductoDTO toDto(Producto model) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());
        dto.setPrecio(model.getPrecio());
        dto.setStock(model.getStock());
        dto.setEliminado(model.getEliminado());

        // Mapear las variantes a DTO si existen
        if (model.getVariantes() != null) {
            dto.setVariantes(model.getVariantes().stream()
                    .map(variante -> productoVarianteMapper.toDto(variante)) // Usamos la instancia inyectada
                    .collect(Collectors.toList())); // Usamos collect para obtener una lista
        }

        // Asignar IDs de categoría y marca
        if (model.getCategoria() != null) {
            dto.setCategoriaId(model.getCategoria().getId());
        }
        if (model.getSubcategoria() != null) { // Agregado para subcategoría
            dto.setSubcategoriaId(model.getSubcategoria().getId());
        }
        if (model.getMarca() != null) {
            dto.setMarcaId(model.getMarca().getId());
        }

        return dto;
    }

    // Convierte de DTO a entidad Producto
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

package programacion.ejemplo.Mapper;

import org.springframework.stereotype.Component;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.DTO.ProductoVarianteDTO;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Marca;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.model.ProductoVariante;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductoMapper {

    // Convierte de entidad a DTO
    public ProductoDTO toDto(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());

        // Mapear las variantes a DTO si existen
        dto.setVariantes(producto.getVariantes().stream()
                .map(ProductoVarianteMapper::toDTO)
                .toList());

        // Asignar IDs de categoría y marca
        dto.setCategoriaId(producto.getCategoria().getId());
        dto.setMarcaId(producto.getMarca().getId());
        return dto;
    }

    // Convierte de DTO a entidad Producto
    public Producto toEntity(ProductoDTO dto, Categoria categoria, Marca marca) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        return producto;
    }
}


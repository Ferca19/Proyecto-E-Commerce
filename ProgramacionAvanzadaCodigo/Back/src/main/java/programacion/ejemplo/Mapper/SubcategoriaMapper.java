package programacion.ejemplo.Mapper;


import programacion.ejemplo.DTO.SubcategoriaDTO;
import programacion.ejemplo.model.Subcategoria;

public class SubcategoriaMapper {
    public static SubcategoriaDTO toDTO(Subcategoria model) {
        SubcategoriaDTO dto = new SubcategoriaDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());
        dto.setEliminado(model.getEliminado());
        return dto;
    }

    public static Subcategoria toEntity(SubcategoriaDTO dto) {
        Subcategoria model = new Subcategoria();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setDescripcion(dto.getDescripcion());
        model.setEliminado(dto.getEliminado());
        return model;
    }

}
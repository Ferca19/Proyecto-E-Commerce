package programacion.ejemplo.Mapper;


import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.model.Categoria;

public class CategoriaMapper {
    public static CategoriaDTO toDTO(Categoria model) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());
        dto.setEliminado(model.getEliminado());
        return dto;
    }

    public static Categoria toEntity(CategoriaDTO dto) {
        Categoria model = new Categoria();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setDescripcion(dto.getDescripcion());
        model.setEliminado(dto.getEliminado());
        return model;
    }

}
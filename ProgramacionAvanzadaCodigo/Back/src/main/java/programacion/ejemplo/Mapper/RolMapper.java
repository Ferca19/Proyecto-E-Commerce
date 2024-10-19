package programacion.ejemplo.Mapper;


import programacion.ejemplo.DTO.RolDTO;
import programacion.ejemplo.model.Rol;

public class RolMapper {
    public static RolDTO toDTO(Rol model) {
        RolDTO dto = new RolDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());
        dto.setEliminado(model.getEliminado());
        return dto;
    }

    public static Rol toEntity(RolDTO dto) {
        Rol model = new Rol();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setDescripcion(dto.getDescripcion());
        model.setEliminado(dto.getEliminado());
        return model;
    }

}
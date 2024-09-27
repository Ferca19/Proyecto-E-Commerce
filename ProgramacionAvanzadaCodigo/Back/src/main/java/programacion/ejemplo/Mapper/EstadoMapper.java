package programacion.ejemplo.Mapper;

import org.springframework.stereotype.Component;
import programacion.ejemplo.DTO.EstadoDTO;
import programacion.ejemplo.model.Estado;

@Component
public class EstadoMapper {

    // Convertir de Estado a EstadoDTO
    public static EstadoDTO toDTO(Estado model) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());

        // Establecer el estado de eliminación
        dto.setEliminado(model.getEliminado());

        return dto;
    }

    // Convertir de EstadoDTO a Estado
    public static Estado toEntity(EstadoDTO dto) {
        Estado model = new Estado();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setDescripcion(dto.getDescripcion());

        // Establecer el estado de eliminación
        model.setEliminado(dto.getEliminado() != null ? dto.getEliminado() : Estado.NO); // Establecer a NO por defecto

        return model;
    }
}

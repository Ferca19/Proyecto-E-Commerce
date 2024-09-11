package programacion.ejemplo.Mapper;

import programacion.ejemplo.DTO.MarcaDTO;
import programacion.ejemplo.model.Marca;

public class MarcaMapper {
    public static MarcaDTO toDTO(Marca model) {
        MarcaDTO dto = new MarcaDTO();
        dto.setId(model.getId());
        dto.setDenominacion(model.getDenominacion());
        dto.setObservaciones(model.getObservaciones());
        dto.setEliminado(model.getEliminado());
        return dto;
    }

    public static Marca toEntity(MarcaDTO dto) {
        Marca model = new Marca();
        model.setId(dto.getId());
        model.setDenominacion(dto.getDenominacion());
        model.setObservaciones(dto.getObservaciones());
        model.setEliminado(dto.getEliminado());
        return model;
    }

}

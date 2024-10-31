package programacion.ejemplo.Mapper;

import programacion.ejemplo.DTO.AjusteInventarioDTO;
import programacion.ejemplo.DTO.DetalleAjusteInventarioDTO;
import programacion.ejemplo.model.AjusteInventario;
import programacion.ejemplo.model.DetalleAjusteInventario;
import programacion.ejemplo.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class AjusteInventarioMapper {

    // Método para convertir de AjusteInventario a AjusteInventarioDTO
    public static AjusteInventarioDTO toDTO(AjusteInventario ajuste) {
        if (ajuste == null) {
            return null;
        }

        AjusteInventarioDTO dto = new AjusteInventarioDTO();
        dto.setId(ajuste.getId());
        dto.setFecha(ajuste.getFecha());
        dto.setRazonAjuste(ajuste.getRazonAjuste());
        dto.setTipoAjuste(ajuste.getTipoAjuste());
        dto.setEliminado(ajuste.getEliminado());
        dto.setUsuarioId(ajuste.getUsuario().getId()); // Asumiendo que Usuario tiene un método getId()

        // Convertir la lista de detalles
        List<DetalleAjusteInventarioDTO> detallesDTO = ajuste.getDetalles()
                .stream()
                .map(DetalleAjusteInventarioMapper::toDTO) // Usar el mapper de detalle
                .collect(Collectors.toList());
        dto.setDetalles(detallesDTO);

        return dto;
    }

    // Método para convertir de AjusteInventarioDTO a AjusteInventario
    public static AjusteInventario toEntity(AjusteInventarioDTO dto) {
        if (dto == null) {
            return null;
        }

        AjusteInventario ajuste = new AjusteInventario();
        ajuste.setId(dto.getId());
        ajuste.setFecha(dto.getFecha());
        ajuste.setRazonAjuste(dto.getRazonAjuste());
        ajuste.setTipoAjuste(dto.getTipoAjuste());
        ajuste.setEliminado(dto.getEliminado());
        // Asumiendo que tienes acceso al Usuario y puedes obtenerlo por su ID
        Usuario usuario = new Usuario();
        usuario.setId(dto.getUsuarioId());
        ajuste.setUsuario(usuario);

        // Convertir la lista de detalles
        List<DetalleAjusteInventario> detalles = dto.getDetalles()
                .stream()
                .map(DetalleAjusteInventarioMapper::toEntity) // Usar el mapper de detalle
                .collect(Collectors.toList());
        ajuste.setDetalles(detalles);

        return ajuste;
    }
}

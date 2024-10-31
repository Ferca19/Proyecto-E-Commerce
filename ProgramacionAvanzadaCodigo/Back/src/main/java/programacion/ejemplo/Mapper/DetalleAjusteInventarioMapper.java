package programacion.ejemplo.Mapper;

import programacion.ejemplo.DTO.DetalleAjusteInventarioDTO;
import programacion.ejemplo.model.DetalleAjusteInventario;

public class DetalleAjusteInventarioMapper {

    // Convertir de DetalleAjusteInventario a DetalleAjusteInventarioDTO
    public static DetalleAjusteInventarioDTO toDTO(DetalleAjusteInventario detalle) {
        if (detalle == null) {
            return null;
        }

        DetalleAjusteInventarioDTO dto = new DetalleAjusteInventarioDTO();
        dto.setId(detalle.getId());
        dto.setCantidadAjustada(detalle.getCantidadAjustada());
        dto.setEliminado(detalle.getEliminado());
        dto.setAjusteInventarioId(detalle.getAjusteInventario().getId());
        dto.setProductoId(detalle.getProducto().getId()); // Asumiendo que Producto tiene un método getId()

        return dto;
    }

    // Convertir de DetalleAjusteInventarioDTO a DetalleAjusteInventario
    public static DetalleAjusteInventario toEntity(DetalleAjusteInventarioDTO dto) {
        if (dto == null) {
            return null;
        }

        DetalleAjusteInventario detalle = new DetalleAjusteInventario();
        detalle.setId(dto.getId());
        detalle.setCantidadAjustada(dto.getCantidadAjustada());
        detalle.setEliminado(dto.getEliminado());

        // Aquí se puede establecer el AjusteInventario y el Producto si se tiene acceso a sus entidades.

        return detalle;
    }
}

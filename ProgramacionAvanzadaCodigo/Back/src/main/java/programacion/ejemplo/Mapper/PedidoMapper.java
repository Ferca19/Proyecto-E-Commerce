package programacion.ejemplo.Mapper;

import org.springframework.stereotype.Component;
import programacion.ejemplo.DTO.PedidoDTO;
import programacion.ejemplo.model.Pedido;

@Component
public class PedidoMapper {

    // Convertir de Pedido a PedidoDTO
    public static PedidoDTO toDTO(Pedido model) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(model.getId());
        dto.setFechaYHora(model.getFechaYHora());
        dto.setImporteTotal(model.getImporteTotal());

        // Mapear detalles del pedido como objetos completos
        if (model.getDetallesPedido() != null && !model.getDetallesPedido().isEmpty()) {
            dto.setDetallesPedido(model.getDetallesPedido()
                    .stream()
                    .map(DetallePedidoMapper::toDTO)
                    .toList());
        }

        // Mapear el usuario como un objeto completo
        if (model.getUsuario() != null) {
            dto.setUsuario(UsuarioMapper.toDTO(model.getUsuario()));
        }

        // Mapear el estado como un objeto completo
        if (model.getEstado() != null) {
            dto.setEstado(EstadoMapper.toDTO(model.getEstado()));
        }

        dto.setEliminado(model.getEliminado());

        return dto;
    }

    // Convertir de PedidoDTO a Pedido
    public static Pedido toEntity(PedidoDTO dto) {
        Pedido model = new Pedido();
        model.setId(dto.getId());
        model.setFechaYHora(dto.getFechaYHora());
        model.setImporteTotal(dto.getImporteTotal());

        // Mapear detalles del pedido como objetos completos
        if (dto.getDetallesPedido() != null && !dto.getDetallesPedido().isEmpty()) {
            model.setDetallesPedido(dto.getDetallesPedido()
                    .stream()
                    .map(DetallePedidoMapper::toEntity)
                    .toList());
        }

        // Mapear el usuario desde el DTO a la entidad
        if (dto.getUsuario() != null) {
            model.setUsuario(UsuarioMapper.toEntity(dto.getUsuario()));
        }

        // Mapear el estado desde el DTO a la entidad
        if (dto.getEstado() != null) {
            model.setEstado(EstadoMapper.toEntity(dto.getEstado()));
        }

        model.setEliminado(dto.getEliminado() != null ? dto.getEliminado() : Pedido.NO); // Establecer a NO por defecto

        return model;
    }
}

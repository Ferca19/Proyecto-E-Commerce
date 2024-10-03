package programacion.ejemplo.Mapper;


import org.springframework.stereotype.Component;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.model.DetallePedido;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetallePedidoMapper {

    // Convertir de DetallePedido a DetallePedidoDTO
    public static DetallePedidoDTO toDTO(DetallePedido model) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId(model.getId());
        dto.setProductoId(model.getProducto() != null ? model.getProducto().getId() : null);
        dto.setCantidad(model.getCantidad());
        dto.setSubtotal(model.getSubtotal());

        // Establecer el estado de eliminación
        dto.setEliminado(model.getEliminado());


        return dto;
    }

    // Convertir de DetallePedidoDTO a DetallePedido
    public static DetallePedido toEntity(DetallePedidoDTO dto) {
        DetallePedido model = new DetallePedido();
        model.setId(dto.getId());
        model.setCantidad(dto.getCantidad());
        model.setSubtotal(dto.getSubtotal());

        // Aquí puedes manejar la asignación del producto, si es necesario
        // Por ejemplo, buscar el producto por ID y asignarlo a model.setProducto(...)

        // Establecer el estado de eliminación
        model.setEliminado(dto.getEliminado() != null ? dto.getEliminado() : DetallePedido.NO); // Establecer a NO por defecto

        return model;
    }

}
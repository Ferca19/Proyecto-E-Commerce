package programacion.ejemplo.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PedidoDTO {
    private Integer id; // ID del pedido

    @NotNull
    private Date fechaYHora; // Fecha y hora del pedido

    @NotNull
    private double importeTotal; // Importe total del pedido

    // Relación con detalles del pedido
    private List<DetallePedidoDTO> detallesPedido; // Lista de detalles de pedido

    private UsuarioDTO usuario;

    private EstadoDTO estado;

    private Integer eliminado;
}

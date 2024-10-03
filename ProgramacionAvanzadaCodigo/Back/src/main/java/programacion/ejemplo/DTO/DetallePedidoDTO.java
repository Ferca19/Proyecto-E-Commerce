package programacion.ejemplo.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetallePedidoDTO {

    private Integer id; // ID del detalle del pedido

    @NotNull
    private Integer productoId; // ID del producto

    @NotNull
    @Min(1) // Asegura que la cantidad mínima sea 1
    private Integer cantidad; // Cantidad de productos

    private double subtotal; // Subtotal del detalle del pedido

    // Estado de eliminación (opcional; se puede establecer por defecto)
    private Integer eliminado; // Se agrega el campo de eliminado


}
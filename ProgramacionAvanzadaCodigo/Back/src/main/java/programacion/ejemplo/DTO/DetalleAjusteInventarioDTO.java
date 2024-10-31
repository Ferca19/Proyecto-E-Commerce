package programacion.ejemplo.DTO;

import lombok.Data;
import java.util.Set;

@Data
public class DetalleAjusteInventarioDTO {

    private Integer id;
    private int cantidadAjustada;
    private int eliminado;

    // ID del ajuste al que pertenece este detalle
    private Integer ajusteInventarioId;

    // ID del producto asociado a este detalle
    private Integer productoId;
}
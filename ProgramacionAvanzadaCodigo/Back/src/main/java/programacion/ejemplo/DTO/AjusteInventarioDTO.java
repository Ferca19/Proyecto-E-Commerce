package programacion.ejemplo.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AjusteInventarioDTO {

    private Integer id;
    private Date fecha;
    private String razonAjuste;
    private int tipoAjuste;
    private int eliminado;

    // ID del usuario para simplificar la referencia y evitar cargar toda la entidad
    private Integer usuarioId;

    // Lista de detalles asociados al ajuste
    private List<DetalleAjusteInventarioDTO> detalles;
}
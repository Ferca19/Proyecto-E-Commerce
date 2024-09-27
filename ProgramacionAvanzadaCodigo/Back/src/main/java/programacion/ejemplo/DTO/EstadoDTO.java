package programacion.ejemplo.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoDTO {

    private Integer id; // ID del estado

    @NotNull
    private String nombre; // Nombre del estado

    @NotNull
    private String descripcion; // Descripción del estado

    // Estado de eliminación (opcional; se puede establecer por defecto)
    private Integer eliminado; // Se agrega el campo de eliminado

}

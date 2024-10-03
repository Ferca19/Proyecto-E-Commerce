package programacion.ejemplo.DTO;
import lombok.Data;

@Data
public class ActualizarProductoDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private String tamano;
    private String color;
    private String imagen;
    private Integer categoriaId;
    private Integer subcategoriaId;
    private Integer marcaId;
}

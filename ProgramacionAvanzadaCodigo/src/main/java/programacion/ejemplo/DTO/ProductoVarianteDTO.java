package programacion.ejemplo.DTO;

import lombok.Data;

@Data
public class ProductoVarianteDTO {

    private Integer id;
    private String nombreVariante; // Ej. "Color", "Talla", "Pulgadas"
    private String valorVariante;    // Ej. "Rojo", "L", "42 pulgadas"
    private Integer productoId;
}
package programacion.ejemplo.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProductoDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private List<ProductoVarianteDTO> variantes;
    private Integer categoriaId;
    private Integer marcaId;

}
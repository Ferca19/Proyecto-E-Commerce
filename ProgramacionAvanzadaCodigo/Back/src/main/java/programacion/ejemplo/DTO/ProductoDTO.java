package programacion.ejemplo.DTO;

import lombok.Data;


@Data
public class ProductoDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private int eliminado;
    private Integer categoriaId;
    private Integer subcategoriaId;
    private Integer marcaId;

}
package programacion.ejemplo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class ProductoVariante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombreVariante; // Ejemplo: "Tamaño", "Color", "Litros"

    @NotNull
    private String valorVariante; // Ejemplo: "43\"", "Rojo", "1L"

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

}

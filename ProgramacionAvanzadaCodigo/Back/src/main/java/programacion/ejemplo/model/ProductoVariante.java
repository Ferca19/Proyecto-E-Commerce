package programacion.ejemplo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "producto_variante_producto",
            joinColumns = @JoinColumn(name = "producto_variante_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;

}

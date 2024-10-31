package programacion.ejemplo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;


import java.util.Set;


@Entity
@Data
@ToString
public class DetalleAjusteInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int cantidadAjustada;

    @NotNull
    private int eliminado;
    public static final int NO=0;
    public static final int SI=1;

    public void asEliminar() {
        this.setEliminado(1);
    }


    // Relación con Producto (un detalle tiene un producto)
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Relación con AjusteInventario (un detalle pertenece a un ajuste)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ajuste_inventario_id", nullable = false)
    @JsonBackReference // Para evitar la serialización cíclica
    @ToString.Exclude  // Excluir de toString() para evitar referencia cíclica
    private AjusteInventario ajusteInventario;
}

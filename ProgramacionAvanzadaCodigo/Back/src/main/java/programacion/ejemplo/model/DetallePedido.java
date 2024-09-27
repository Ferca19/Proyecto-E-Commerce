package programacion.ejemplo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
@Entity
@Data
@ToString
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private double subtotal;

    @NotNull
    @Min(1)  // Asegura que la cantidad mínima sea 1
    private Integer cantidad;

    @NotNull
    private int eliminado;
    public static final int NO = 0;
    public static final int SI = 1;

    public void asEliminar() {
        this.setEliminado(1);
    }


    // Relación con Pedido (Muchos DetallePedido pertenecen a un Pedido)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonBackReference // Para evitar la serialización cíclica
    @ToString.Exclude  // Excluir de toString() para evitar referencia cíclica
    private Pedido pedido;

    // Relación con Producto (Muchos DetallePedido tienen un Producto)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @ToString.Exclude  // Evitar sobrecarga de datos en toString()
    private Producto producto;

}

package programacion.ejemplo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Data
@ToString
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Date fechaYHora;

    @NotNull
    private double importeTotal;


    @NotNull
    private int eliminado;
    public static final int NO = 0;
    public static final int SI = 1;

    public void asEliminar() {
        this.setEliminado(1);
    }

    // Relación con DetallePedido (Un Pedido tiene muchos detalles)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference // Para evitar la serialización cíclica
    @ToString.Exclude  // Excluir de toString() para evitar referencia cíclica
    private List<DetallePedido> detallesPedido;

    // Relación con Usuario (Un Pedido pertenece a un solo Usuario)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    @ToString.Exclude  // Excluir de toString() para evitar sobrecarga de datos
    private Usuario usuario;

    // Relación con Estado (Un Pedido tiene un solo Estado)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", nullable = false)
    @ToString.Exclude  // Excluir de toString() para evitar referencia cíclica
    private Estado estado;

}

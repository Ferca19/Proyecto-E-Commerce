package programacion.ejemplo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;


@Entity
@Data
@ToString
public class AjusteInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date fecha;

    private String razonAjuste;

    private int tipoAjuste;

    @NotNull
    private int eliminado;
    public static final int NO=0;
    public static final int SI=1;

    public void asEliminar() {
        this.setEliminado(1);
    }


    // Relación con Usuario (un ajuste tiene solo un usuario)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore // Evita la recursión en la serialización JSON
    private Usuario usuario;

    // Relación con DetalleAjusteInventario (un ajuste tiene muchos detalles)
    @OneToMany(mappedBy = "ajusteInventario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleAjusteInventario> detalles;

}

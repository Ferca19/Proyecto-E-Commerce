package programacion.ejemplo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;

    @NotNull
    private int eliminado;
    public static final int NO=0;
    public static final int SI=1;

    public void asEliminar() {
        this.setEliminado(1);
    }

    // Relación con Usuario (Un Rol puede tener muchos Usuarios)
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore  // Evita problemas de serialización recursiva
    @ToString.Exclude  // Excluir de toString() para evitar sobrecarga de datos
    private List<Usuario> usuarios;

}

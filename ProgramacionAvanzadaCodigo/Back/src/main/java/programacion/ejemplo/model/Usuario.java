package programacion.ejemplo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    @NotNull
    private String mail;

    @NotNull
    private String contrasena;

    @NotNull
    private int eliminado;
    public static final int NO = 0;
    public static final int SI = 1;

    public void asEliminar() {
        this.setEliminado(1);
    }


    // Relación con Pedido (Un Usuario tiene muchos Pedidos)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Pedido> pedidos;

    // Relación con Rol (Un Usuario tiene un solo Rol)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")  // Eliminado nullable = false
    @ToString.Exclude  // Excluir de toString() para evitar sobrecarga de datos
    private Rol rol;

    // Relación con AjusteInventario (un usuario puede tener muchos ajustes)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<AjusteInventario> ajustesInventario;
}
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
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String denominacion;
    private String observaciones;

    @NotNull
    private int estado;
        public static final int COMUN=0;
        public static final int ELIMINADO=1;
        public void asEliminar() {
             this.setEstado(1);
       }



    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Producto> productos;
}

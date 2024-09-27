package programacion.ejemplo.DTO;
import lombok.Data;

@Data
public class RegisterDTO {
    private String nombre;
    private String apellido;
    private String mail;
    private String contrasena;
    //private String rol;
}

package programacion.ejemplo.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer id; // ID del usuario

    @NotNull
    private String nombre; // Nombre del usuario

    @NotNull
    private String apellido; // Apellido del usuario

    @NotNull
    private String mail; // Correo electrónico del usuario

    @NotNull
    private String contrasena; // Contraseña del usuario

    private int eliminado; // Estado de eliminación (opcional en el DTO)
}
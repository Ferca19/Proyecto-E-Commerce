package programacion.ejemplo.DTO;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private UsuarioDTO usuario;

    // Constructor que acepta token y usuarioDTO
    public LoginResponseDTO(String token, UsuarioDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }
}

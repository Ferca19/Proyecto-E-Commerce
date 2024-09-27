package programacion.ejemplo.DTO;

import lombok.Data;

@Data
public class LoginReponseDTO {
    private String token;
    private UsuarioDTO user;
}

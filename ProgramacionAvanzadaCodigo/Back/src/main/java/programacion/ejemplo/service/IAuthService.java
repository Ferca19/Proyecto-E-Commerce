package programacion.ejemplo.service;

import programacion.ejemplo.DTO.LoginDTO;
import programacion.ejemplo.DTO.LoginResponseDTO;
import programacion.ejemplo.DTO.RegisterDTO;
import programacion.ejemplo.DTO.UsuarioDTO;

public interface IAuthService {
    UsuarioDTO registrarUsuario(RegisterDTO registerDTO);

    LoginResponseDTO login(LoginDTO loginDTO);

    UsuarioDTO actualizarUsuario(Integer usuarioId, UsuarioDTO datosActualizadosDTO);

    UsuarioDTO obtenerUsuarioAutenticado();
}

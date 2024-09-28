package programacion.ejemplo.service;

import programacion.ejemplo.DTO.LoginDTO;
import programacion.ejemplo.DTO.LoginResponseDTO;

public interface IAuthService {

    LoginResponseDTO login(LoginDTO loginDTO);
}

package programacion.ejemplo.service;

import programacion.ejemplo.DTO.LoginDTO;

public interface IAuthService {

    String login(LoginDTO loginDTO);
}

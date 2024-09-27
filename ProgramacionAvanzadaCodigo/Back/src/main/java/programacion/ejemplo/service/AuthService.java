package programacion.ejemplo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import programacion.ejemplo.DTO.LoginDTO;
import programacion.ejemplo.util.JwtUtil;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService; // Para cargar los detalles del usuario

    @Autowired
    private JwtUtil jwtUtil; // Utilidad para generar el JWT

    public String login(LoginDTO loginDTO) {
        // Autenticación
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getMail(), loginDTO.getContrasena())
        );

        // Cargar los detalles del usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getMail());
        return jwtUtil.generateToken(userDetails); // Generar y devolver el token
    }
}

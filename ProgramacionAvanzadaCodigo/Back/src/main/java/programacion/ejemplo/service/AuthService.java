package programacion.ejemplo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import programacion.ejemplo.DTO.LoginDTO;
import programacion.ejemplo.DTO.LoginResponseDTO;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.util.JwtUtil;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService; // Para cargar los detalles del usuario

    @Autowired
    private JwtUtil jwtUtil; // Utilidad para generar el JWT

    public LoginResponseDTO login(LoginDTO loginDTO) {
        // Autenticación
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getMail(), loginDTO.getContrasena())
        );

        // Cargar los detalles del usuario
        CustomUserDetailsService.CustomUserDetails userDetails =
                (CustomUserDetailsService.CustomUserDetails) userDetailsService.loadUserByUsername(loginDTO.getMail());

        // Obtener el usuario directamente desde CustomUserDetails
        Usuario usuario = userDetails.getUsuario();

        // Crear el DTO del usuario
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setMail(usuario.getMail());
        usuarioDTO.setContrasena(usuario.getContrasena()); // Omitir si es sensible
        usuarioDTO.setEliminado(usuario.getEliminado());

        // Generar el token
        String token = jwtUtil.generateToken(userDetails);

        // Devolver la respuesta que incluye el token y el usuarioDTO
        return new LoginResponseDTO(token, usuarioDTO);
    }
}

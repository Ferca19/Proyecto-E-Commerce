package programacion.ejemplo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import programacion.ejemplo.DTO.LoginDTO;
import programacion.ejemplo.DTO.LoginResponseDTO;
import programacion.ejemplo.DTO.RegisterDTO;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.Mapper.UsuarioMapper;
import programacion.ejemplo.config.JwtTokenUtil;
import programacion.ejemplo.model.Usuario;


@Service
public class AuthService implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Para cargar los detalles del usuario


    @Autowired
    private JwtTokenUtil jwtUtil; // Utilidad para generar el JWT

    @Autowired
    private IUsuarioService usuarioService;

    public UsuarioDTO registrarUsuario(RegisterDTO registerDTO) {

        return usuarioService.crearUsuario(registerDTO);
    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        try {
            // Autenticación
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getMail(), loginDTO.getContrasena())
            );
        } catch (BadCredentialsException e) {
            // Manejar el caso de credenciales inválidas
            throw new RuntimeException("Invalid username or password", e);
        }

        // Cargar los detalles del usuario
        CustomUserDetailsService.CustomUserDetails userDetails =
                (CustomUserDetailsService.CustomUserDetails) customUserDetailsService.loadUserByUsername(loginDTO.getMail());

        // Obtener el usuario directamente desde CustomUserDetails
        Usuario usuario = userDetails.getUsuario();

        // Crear el DTO del usuario
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setMail(usuario.getMail());
        usuarioDTO.setEliminado(usuario.getEliminado());

        // Generar el token
        String token = jwtUtil.generateToken(userDetails);

        // Devolver la respuesta que incluye el token y el usuarioDTO
        return new LoginResponseDTO(token, usuarioDTO);
    }

    public UsuarioDTO actualizarUsuario(Integer usuarioId, UsuarioDTO datosActualizadosDTO) {
        // Llamar a usuarioService para actualizar los datos del usuario autenticado
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuarioId, datosActualizadosDTO);

        // Convertir el Usuario actualizado a UsuarioDTO
        return UsuarioMapper.toDTO(usuarioActualizado);
    }

    public UsuarioDTO obtenerUsuarioAutenticado() {
        // Cargar los detalles del usuario desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No hay un usuario autenticado en el contexto de seguridad");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetailsService.CustomUserDetails) {
            CustomUserDetailsService.CustomUserDetails userDetails = (CustomUserDetailsService.CustomUserDetails) principal;

            // Obtener el usuario directamente desde CustomUserDetails
            Usuario usuario = userDetails.getUsuario();

            return UsuarioMapper.toDTO(usuario); // Convertir a DTO
        } else {
            throw new IllegalStateException("Usuario autenticado no encontrado o el principal no es del tipo esperado");
        }
    }


}

package programacion.ejemplo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.UsuarioRepository;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aquí se busca el usuario por el correo
        Usuario usuario = usuarioRepository.findByMail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new CustomUserDetails(usuario);
    }


    public static class CustomUserDetails extends org.springframework.security.core.userdetails.User {
        private final Usuario usuario;

        public CustomUserDetails(Usuario usuario) {
            super(usuario.getMail(), usuario.getContrasena(), new ArrayList<>());
            this.usuario = usuario;
        }

        public Usuario getUsuario() {
            return usuario;
        }
    }
}

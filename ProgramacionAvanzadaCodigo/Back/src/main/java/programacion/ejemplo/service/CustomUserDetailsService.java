package programacion.ejemplo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.model.Rol;
import programacion.ejemplo.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario por correo electrónico (mail)
        Usuario usuario = usuarioRepository.findByMail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new CustomUserDetails(usuario);
    }

    public static class CustomUserDetails extends org.springframework.security.core.userdetails.User {
        private final Usuario usuario;

        public CustomUserDetails(Usuario usuario) {
            super(usuario.getMail(), usuario.getContrasena(), getAuthorities(usuario)); // Añadir autoridades
            this.usuario = usuario;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        // Método para obtener las autoridades (roles) del usuario
        private static Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
            List<GrantedAuthority> authorities = new ArrayList<>();

            // Obtener el rol del usuario y añadirlo a las autoridades
            Rol rol = usuario.getRol(); // Aquí se asume que Usuario tiene una relación ManyToOne con Rol
            authorities.add(new SimpleGrantedAuthority(rol.getNombre()));

            return authorities;
        }
    }
}

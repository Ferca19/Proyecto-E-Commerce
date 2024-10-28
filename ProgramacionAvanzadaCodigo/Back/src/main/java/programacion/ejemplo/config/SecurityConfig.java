package programacion.ejemplo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import programacion.ejemplo.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt es el encoder recomendado
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register", "/auth/actualizar-usuario").permitAll() // Permitir acceso a login y registro

                        .requestMatchers("/roles/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/roles/public/**").hasAnyAuthority("Administrador", "Cliente")

                        .requestMatchers("/productos/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/productos/public/**").hasAnyAuthority("Administrador", "Cliente")

                        .requestMatchers("/categorias/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/categorias/public/**").hasAnyAuthority("Administrador", "Cliente")

                        .requestMatchers("/estado/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/estado/public/**").hasAnyAuthority("Administrador", "Cliente")

                        .requestMatchers("/marca/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/marca/public/**").hasAnyAuthority("Administrador", "Cliente")

                        .requestMatchers("/pedidos/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/pedidos/public/**").hasAnyAuthority("Administrador", "Cliente")

                        .requestMatchers("/subcategorias/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/subcategorias/public/**").hasAnyAuthority("Administrador", "Cliente")

                        .requestMatchers("/usuarios/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/usuarios/public/**").hasAnyAuthority("Administrador", "Cliente")

                        .requestMatchers("/perfil/**").authenticated() // Acceso solo para usuarios autenticados

                        .anyRequest().permitAll() // Permitir acceso a todas las demás rutas
                )
                .formLogin(login -> login.disable()) // Deshabilitar formulario de login
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Agregar el filtro JWT antes del filtro de autenticación por username y password

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    // =============================== sin proteccion ==================================
    /*

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permitir todas las rutas sin autenticación
                )
                .formLogin(login -> login.disable()); // Deshabilitar formulario de login

        return http.build();
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService) // Configurar el UserDetailsService
                .passwordEncoder(passwordEncoder()); // Configurar el PasswordEncoder
        return authenticationManagerBuilder.build();
    }

     */

}

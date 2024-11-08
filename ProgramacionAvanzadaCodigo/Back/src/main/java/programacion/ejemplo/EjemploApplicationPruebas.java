package programacion.ejemplo;

import org.hibernate.annotations.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import programacion.ejemplo.repository.UsuarioRepository;


@SpringBootApplication
@ComponentScan(
		basePackages = "programacion.ejemplo", // Escanea todo el paquete programacion.ejemplo
		excludeFilters = {
				@ComponentScan.Filter(
						type = FilterType.REGEX,
						pattern = "programacion.ejemplo.controller.AuthController" // Excluye solo el controlador authController
				),@ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern = "programacion.ejemplo.service.AuthService" // Excluye solo el controlador authController
				),@ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern = "programacion.ejemplo.controller.UsuarioController" // Excluye solo el controlador authController
				),@ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern = "programacion.ejemplo.service.UsuarioService" // Excluye solo el controlador authController
				),@ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern = "programacion.ejemplo.service.CustomUserDetailsService" // Excluye solo el controlador authController
				),@ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern = "programacion.ejemplo.config.JwtAuthenticationFilter" // Excluye solo el controlador authController
				),@ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern = "programacion.ejemplo.config.JwtTokenUtil" // Excluye solo el controlador authController
				),@ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern = "programacion.ejemplo.config.SecurityConfig" // Excluye solo el controlador authController
				),@ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern = "programacion.ejemplo.config.WebConfig" // Excluye solo el controlador authController
				)
		}
)
@Profile("test") // Solo se ejecutará cuando se active el perfil 'test'
@EnableJpaRepositories(
		basePackages = "programacion.ejemplo.repository",
		excludeFilters = @ComponentScan.Filter(
		type = FilterType.REGEX,
		pattern = "programacion.ejemplo.repository.UsuarioRepository" // Excluye el repositorio solo en pruebas
)
)
@Import(TestSecurityConfig.class)
public class EjemploApplicationPruebas {
	public static void main(String[] args) {
		SpringApplication.run(EjemploApplicationPruebas.class, args);
	}
}

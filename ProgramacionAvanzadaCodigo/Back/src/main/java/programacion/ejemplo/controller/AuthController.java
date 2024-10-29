package programacion.ejemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.LoginDTO;
import programacion.ejemplo.DTO.LoginResponseDTO;
import programacion.ejemplo.DTO.RegisterDTO;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.service.IAuthService;

@RestController
@RequestMapping("auth")
@CrossOrigin(value="http://localhost:5173")

public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    private IAuthService authService;


    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody RegisterDTO registerDTO) {
        UsuarioDTO createdUser = authService.registrarUsuario(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            LoginResponseDTO response = authService.login(loginDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // o un mensaje más específico
        }
    }

    @PutMapping("/actualizar-usuario")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        // Extraer el usuario autenticado del token
        UsuarioDTO usuarioAutenticado = authService.obtenerUsuarioAutenticado();

        // Llamar al servicio para actualizar el usuario con su ID
        UsuarioDTO usuarioActualizado = authService.actualizarUsuario(usuarioAutenticado.getId(), usuarioDTO);

        return ResponseEntity.ok(usuarioActualizado);
    }

}

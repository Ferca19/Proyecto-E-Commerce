package programacion.ejemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.LoginDTO;
import programacion.ejemplo.DTO.RegisterDTO;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.service.IAuthService;
import programacion.ejemplo.service.IUsuarioService;
import programacion.ejemplo.service.UsuarioService;

@RestController
@RequestMapping("auth")
@CrossOrigin(value=" http://localhost:5173")

public class AuthController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IAuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody RegisterDTO registerDTO) {
        UsuarioDTO createdUser = usuarioService.crearUsuario(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        return ResponseEntity.ok(token); // Devuelve el token como respuesta
    }

}

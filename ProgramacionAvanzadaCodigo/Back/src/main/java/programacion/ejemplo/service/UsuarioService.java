package programacion.ejemplo.service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.PedidoDTO;
import programacion.ejemplo.DTO.RegisterDTO;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.Mapper.PedidoMapper;
import programacion.ejemplo.Mapper.UsuarioMapper;
import programacion.ejemplo.exception.RecursoNoEncontradoExcepcion;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Rol;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.RolRepository;
import programacion.ejemplo.repository.UsuarioRepository;


import java.util.List;
import java.util.Optional;

@Service

public class UsuarioService implements IUsuarioService {


    @Autowired
    @Lazy
    private IPedidoService pedidoService;

    @Autowired
    private UsuarioRepository modelRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public PedidoDTO crearPedido(Integer usuarioId, List<DetallePedidoDTO> detallesPedidoDTO) {
        Pedido pedido = pedidoService.crearPedido(usuarioId, detallesPedidoDTO);
        return PedidoMapper.toDTO(pedido);
    }

    // Método para obtener un usuario por su ID
    @Override
    public UsuarioDTO obtenerPorId(Integer usuarioId) {
        // Buscar el usuario por ID y asegurarse de que no esté eliminado
        Optional<Usuario> usuarioOpt = modelRepository.findByIdAndEliminadoFalse(usuarioId);

        if (usuarioOpt.isPresent()) {
            // Si se encuentra, convertirlo en DTO y devolverlo
            return UsuarioMapper.toDTO(usuarioOpt.get()); // Llamar directamente a la clase
        } else {
            // Manejar el caso donde no se encuentra el usuario
            throw new EntityNotFoundException("Usuario no encontrado con ID (metodo obtener por id): " + usuarioId);
        }
    }

    public UsuarioDTO crearUsuario(RegisterDTO registerDTO) {
        // Verificar si el email ya está registrado
        if (modelRepository.existsByMail(registerDTO.getMail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(registerDTO.getNombre());
        usuario.setApellido(registerDTO.getApellido());
        usuario.setMail(registerDTO.getMail());

        // Hashear la contraseña
        String hashedPassword = passwordEncoder.encode(registerDTO.getContrasena());
        usuario.setContrasena(hashedPassword);

        // Asignar rol por defecto (ID 2)
        Rol rolPredeterminado = rolRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Rol con ID 2 no encontrado"));
        usuario.setRol(rolPredeterminado);

        // Guardar usuario en la base de datos
        modelRepository.save(usuario);

        return usuarioMapper.toDTO(usuario);
    }

    @Transactional
    public Usuario actualizarUsuario(Integer usuarioId, UsuarioDTO usuarioDTO) {
        // Buscar el usuario existente
        Usuario usuarioExistente = modelRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Usuario no encontrado"));

        // Actualizar solo los campos que están presentes en el DTO
        if (usuarioDTO.getNombre() != null) {
            usuarioExistente.setNombre(usuarioDTO.getNombre());
        }

        if (usuarioDTO.getApellido() != null) {
            usuarioExistente.setApellido(usuarioDTO.getApellido());
        }

        if (usuarioDTO.getMail() != null) {
            usuarioExistente.setMail(usuarioDTO.getMail());
        }

        // Guardar los cambios en el repositorio
        return modelRepository.save(usuarioExistente);
    }

    public void eliminarPedido(Integer pedidoId) {
        pedidoService.eliminarPedido(pedidoId);
    }

    public void recuperarPedido(Integer pedidoId) {
        pedidoService.recuperarPedido(pedidoId);
    }


}

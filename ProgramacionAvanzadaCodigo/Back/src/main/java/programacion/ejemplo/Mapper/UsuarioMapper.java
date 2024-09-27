package programacion.ejemplo.Mapper;

import org.springframework.stereotype.Component;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.model.Usuario;

@Component
public class UsuarioMapper {

    // Convertir de Usuario a UsuarioDTO
    public static UsuarioDTO toDTO(Usuario model) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setApellido(model.getApellido());
        dto.setMail(model.getMail());
        dto.setContrasena(model.getContrasena()); // Nota: Considera si deseas exponer la contraseña en el DTO

        // Establecer el estado de eliminación
        dto.setEliminado(model.getEliminado()); // El estado eliminado se mantiene como un entero (0 o 1)

        return dto;
    }

    // Convertir de UsuarioDTO a Usuario
    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario model = new Usuario();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setApellido(dto.getApellido());
        model.setMail(dto.getMail());
        model.setContrasena(dto.getContrasena());

        // Establecer el estado de eliminación
        model.setEliminado(dto.getEliminado()); // Se mantiene como un entero (0 o 1)

        return model;
    }
}

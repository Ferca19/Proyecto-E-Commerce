package programacion.ejemplo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import programacion.ejemplo.model.Usuario;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository <Usuario,Integer>{
    Usuario findByMail(String mail);
    boolean existsByMail(String mail);
    Optional<Usuario> findByIdAndEliminadoFalse(Integer id);
}

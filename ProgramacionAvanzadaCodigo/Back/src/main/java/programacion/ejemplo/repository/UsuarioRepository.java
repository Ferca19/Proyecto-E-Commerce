package programacion.ejemplo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario,Integer>{
    Usuario findByMail(String mail);
    boolean existsByMail(String mail);
    Optional<Usuario> findByIdAndEliminadoFalse(Integer id);
}

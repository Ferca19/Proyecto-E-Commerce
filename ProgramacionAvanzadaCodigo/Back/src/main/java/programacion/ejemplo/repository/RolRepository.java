package programacion.ejemplo.repository;

import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import programacion.ejemplo.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository  <Rol,Integer>{

    List<Rol> findByEliminado(int eliminado);

    List<Rol> findAllByEliminado(int eliminado);

    Rol findByIdAndEliminado(int id, int eliminado);

    Optional<Rol> findById(int id);

    boolean existsByNombreIgnoreCase(String nombre);

}

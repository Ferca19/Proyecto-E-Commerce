package programacion.ejemplo.repository;

import programacion.ejemplo.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RolRepository extends JpaRepository  <Rol,Integer>{

    List<Rol> findByEliminado(int eliminado);

    List<Rol> findAllByEliminado(int eliminado);

    Rol findByIdAndEliminado(int id, int eliminado);

    boolean existsByNombreIgnoreCase(String nombre);

}

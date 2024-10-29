package programacion.ejemplo.repository;

import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface RolRepository extends JpaRepository  <Rol,Integer>{

    List<Rol> findByEliminado(int eliminado);

    List<Rol> findAllByEliminado(int eliminado);

    Rol findByIdAndEliminado(int id, int eliminado);

    boolean existsByNombreIgnoreCase(String nombre);

}

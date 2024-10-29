package programacion.ejemplo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.Estado;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository <Estado,Integer>{

    List<Estado> findByEliminado(int eliminado);

    List<Estado> findAllByEliminado(int eliminado);

    Estado findByIdAndEliminado(int id, int eliminado);

    boolean existsByNombreIgnoreCase(String nombre);
}

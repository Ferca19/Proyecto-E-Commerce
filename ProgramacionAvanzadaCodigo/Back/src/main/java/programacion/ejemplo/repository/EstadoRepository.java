package programacion.ejemplo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import programacion.ejemplo.model.Estado;

import java.util.List;


public interface EstadoRepository extends JpaRepository <Estado,Integer>{

    List<Estado> findByEliminado(int eliminado);

    List<Estado> findAllByEliminado(int eliminado);

    Estado findByIdAndEliminado(int id, int eliminado);
}

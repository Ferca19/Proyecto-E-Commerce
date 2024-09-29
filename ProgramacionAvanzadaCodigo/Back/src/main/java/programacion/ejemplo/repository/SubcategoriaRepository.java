package programacion.ejemplo.repository;

import programacion.ejemplo.model.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubcategoriaRepository extends JpaRepository  <Subcategoria,Integer>{

    List<Subcategoria> findByEliminado(int eliminado);

    List<Subcategoria> findAllByEliminado(int eliminado);

    Subcategoria findByIdAndEliminado(int id, int eliminado);

    boolean existsByNombreIgnoreCase(String nombre);

}

package programacion.ejemplo.repository;

import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoriaRepository extends JpaRepository  <Subcategoria,Integer>{

    List<Subcategoria> findByEliminado(int eliminado);

    List<Subcategoria> findAllByEliminado(int eliminado);

    Subcategoria findByIdAndEliminado(int id, int eliminado);

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<Subcategoria> findByNombreIgnoreCase(String nombre);
}

package programacion.ejemplo.repository;

import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository  <Categoria,Integer>{

    List<Categoria> findByEliminado(int eliminado);

    List<Categoria> findAllByEliminado(int eliminado);

    Categoria findByIdAndEliminado(int id, int eliminado);

    boolean existsByNombreIgnoreCase(String nombre);

}

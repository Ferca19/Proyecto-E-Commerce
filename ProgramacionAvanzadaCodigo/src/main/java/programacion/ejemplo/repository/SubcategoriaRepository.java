package programacion.ejemplo.repository;

import programacion.ejemplo.model.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubcategoriaRepository extends JpaRepository  <Subcategoria,Integer>{

    List<Subcategoria> findByEliminado(int eliminado);

    // Método para listar subcategorías eliminadas
    List<Subcategoria> findAllByEliminado(int eliminado);

    // Método para recuperar una subcategoría eliminada por ID
    Subcategoria findByIdAndEliminado(int id, int eliminado);

}

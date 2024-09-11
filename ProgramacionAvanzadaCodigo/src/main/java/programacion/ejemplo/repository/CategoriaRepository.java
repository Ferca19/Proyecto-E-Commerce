package programacion.ejemplo.repository;

import programacion.ejemplo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaRepository extends JpaRepository  <Categoria,Integer>{

    List<Categoria> findByEliminado(int eliminado);

    // Método para listar categorías eliminadas
    List<Categoria> findAllByEliminado(int eliminado);

    // Método para recuperar una categoría eliminada por ID
    Categoria findByIdAndEliminado(int id, int eliminado);

}

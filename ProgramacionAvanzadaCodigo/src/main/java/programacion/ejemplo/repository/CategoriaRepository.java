package programacion.ejemplo.repository;

import programacion.ejemplo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaRepository extends JpaRepository  <Categoria,Integer>{

    List<Categoria> findByEstado(int estado);

    // Método para listar categorías eliminadas
    List<Categoria> findAllByEstado(int estado);

    // Método para recuperar una categoría eliminada por ID
    Categoria findByIdAndEstado(int id, int estado);

}

package programacion.ejemplo.repository;

import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository  <Marca,Integer>{
    List<Marca> findByEliminado(int eliminado);

    Marca findByIdAndEliminado(int id, int eliminado);

    List<Marca> findAllByEliminado(int eliminado);

    boolean existsByDenominacionIgnoreCase(String nombre);

    Optional<Marca> findByDenominacionIgnoreCase(String denominacion);
}


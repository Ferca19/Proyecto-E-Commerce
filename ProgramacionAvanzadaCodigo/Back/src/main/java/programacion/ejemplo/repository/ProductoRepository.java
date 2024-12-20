package programacion.ejemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Producto;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    boolean existsByCategoriaId(Integer categoriaId);

    boolean existsByMarcaId(Integer marcaId);

    boolean existsBySubcategoriaId(Integer subcategoriaId);

    List<Producto> findAllById(Iterable<Integer> ids);

    List<Producto> findByEliminado(int eliminado);

    List<Producto> findAllByEliminado(int eliminado);

    Producto findByIdAndEliminado(int id, int eliminado);
}

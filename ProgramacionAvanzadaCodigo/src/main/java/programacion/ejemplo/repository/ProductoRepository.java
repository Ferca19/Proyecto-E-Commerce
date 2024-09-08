package programacion.ejemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.ejemplo.model.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    boolean existsByCategoriaId(Integer categoriaId);
    List<Producto> findAllById(Iterable<Integer> ids);

}

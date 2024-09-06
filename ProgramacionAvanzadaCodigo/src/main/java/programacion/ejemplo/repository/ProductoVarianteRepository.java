package programacion.ejemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.ejemplo.model.ProductoVariante;
import java.util.List;


public interface ProductoVarianteRepository extends JpaRepository<ProductoVariante, Integer> {
    List<ProductoVariante> findByProductoId(Integer productoId);

}

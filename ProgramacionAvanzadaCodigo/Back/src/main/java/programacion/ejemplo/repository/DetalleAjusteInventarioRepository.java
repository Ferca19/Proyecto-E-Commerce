package programacion.ejemplo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.AjusteInventario;
import programacion.ejemplo.model.DetalleAjusteInventario;

@Repository
public interface DetalleAjusteInventarioRepository extends JpaRepository<DetalleAjusteInventario,Integer> {
}

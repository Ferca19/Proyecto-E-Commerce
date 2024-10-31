package programacion.ejemplo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.AjusteInventario;
import programacion.ejemplo.model.Categoria;

@Repository
public interface AjusteInventarioRepository extends JpaRepository<AjusteInventario,Integer> {
}

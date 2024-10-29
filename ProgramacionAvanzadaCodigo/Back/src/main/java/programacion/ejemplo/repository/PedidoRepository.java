package programacion.ejemplo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.Pedido;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository <Pedido,Integer>{
    List<Pedido> findAll();

    boolean existsByEstadoId(Integer estadoId);
}

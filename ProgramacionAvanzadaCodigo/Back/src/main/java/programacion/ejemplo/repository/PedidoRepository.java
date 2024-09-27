package programacion.ejemplo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import programacion.ejemplo.model.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository <Pedido,Integer>{
    List<Pedido> findAll();
}

package programacion.ejemplo.repository;

import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository <DetallePedido,Integer>{

    List<DetallePedido> findByPedidoId(int pedidoId);
}

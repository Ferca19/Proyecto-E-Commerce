package programacion.ejemplo.repository;

import org.springframework.stereotype.Repository;
import programacion.ejemplo.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DetallePedidoRepository extends JpaRepository <DetallePedido,Integer>{
}

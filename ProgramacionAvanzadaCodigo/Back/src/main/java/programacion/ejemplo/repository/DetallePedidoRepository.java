package programacion.ejemplo.repository;

import programacion.ejemplo.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository <DetallePedido,Integer>{
}

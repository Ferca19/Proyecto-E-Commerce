package programacion.ejemplo.service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.PedidoDTO;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.Mapper.PedidoMapper;
import programacion.ejemplo.Mapper.ProductoMapper;
import programacion.ejemplo.Mapper.UsuarioMapper;
import java.util.LinkedHashMap;

import programacion.ejemplo.model.DetallePedido;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.PedidoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class PedidoService implements IPedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository modelRepository;

    @Autowired
    private IEstadoService estadoService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IDetallePedidoService detallePedidoService;

    // Constructor con EstadoService para permitir la inyección en pruebas
    public PedidoService(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    public List<PedidoDTO> getAllPedidos() {
        // Obtener todos los pedidos
        List<Pedido> pedidos = modelRepository.findAll();

        // Filtrar solo los pedidos que no están eliminados (eliminado == 0)
        return pedidos.stream()
                .filter(pedido -> pedido.getEliminado() == 0) // Filtra pedidos eliminados
                .map(PedidoMapper::toDTO) // Mapea a DTO
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Pedido crearPedido(Usuario usuario, List<DetallePedidoDTO> detallesPedidoDTO) {

        // Verificar si el usuario existe
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Verificar si la lista de detalles está vacía
        if (detallesPedidoDTO == null || detallesPedidoDTO.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un detalle de producto.");
        }

        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setFechaYHora(new Date());

        // Asigna el usuario al pedido
        nuevoPedido.setUsuario(usuario);

        // Obtener el estado inicial del pedido
        try {
            nuevoPedido.setEstado(estadoService.obtenerEstadoInicial());
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el estado inicial del pedido.");
        }

        // Guardar el pedido sin el importe total aún
        Pedido pedidoGuardado = modelRepository.save(nuevoPedido);

        // Calcular el importe total y crear los detalles usando DetallePedidoService
        double importeTotal = detallePedidoService.crearDetallePedido(pedidoGuardado, detallesPedidoDTO);

        // Actualizar el importe total del pedido y guardarlo de nuevo
        pedidoGuardado.setImporteTotal(importeTotal);
        modelRepository.save(pedidoGuardado);

        // Volver a consultar el pedido para asegurarse de que se cargan los detalles y relaciones
        Pedido pedidoFinalizado = modelRepository.findById(pedidoGuardado.getId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        return pedidoFinalizado;
    }

    public void eliminarPedido(Integer pedidoId) {
        // Obtener el pedido por ID
        Pedido pedido = modelRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + pedidoId));

        // Cambiar el estado de eliminación a 1 (eliminado)
        pedido.setEliminado(1);

        // Recorrer los detalles del pedido y actualizar el stock de los productos
        for (DetallePedido detalle : pedido.getDetallesPedido()) {
            // Obtener el producto correspondiente
            Producto producto = detalle.getProducto();

            // Sumar la cantidad de productos devueltos al stock
            producto.setStock(producto.getStock() + detalle.getCantidad());

            // Actualizar el producto en la base de datos
            productoService.actualizarStockProducto(producto);
        }

        // Guardar los cambios del pedido
        modelRepository.save(pedido);
    }

    public void recuperarPedido(Integer pedidoId) {
        // Obtener el pedido por ID
        Pedido pedido = modelRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + pedidoId));

        // Cambiar el estado de eliminación a 0
        pedido.setEliminado(0);

        // Recorrer los detalles del pedido y actualizar el stock de los productos
        for (DetallePedido detalle : pedido.getDetallesPedido()) {
            // Obtener el producto correspondiente
            Producto producto = detalle.getProducto();

            // Sumar la cantidad de productos devueltos al stock
            producto.setStock(producto.getStock() - detalle.getCantidad());

            // Actualizar el producto en la base de datos
            productoService.actualizarStockProducto(producto);
        }

        // Guardar los cambios del pedido
        modelRepository.save(pedido);
    }

    @Override
    public boolean existePorEstadoId(Integer estadoId) {
        return modelRepository.existsByEstadoId(estadoId);
    }

    @Override
    public Map<String, Object> generarInforme() {
        Map<String, Object> informe = new HashMap<>();

        // Total de pedidos
        long totalPedidos = modelRepository.count();
        informe.put("totalPedidos", totalPedidos);

        // Productos más vendidos
        List<DetallePedido> detalles = detallePedidoService.obtenerTodosDetalles();
        Map<String, Long> productosVendidos = detalles.stream()
                .collect(Collectors.groupingBy(detalle -> detalle.getProducto().getNombre(), Collectors.summingLong(DetallePedido::getCantidad)));

        // Ordenar por cantidad vendida y tomar los más vendidos
        Map<String, Long> productosMasVendidos = productosVendidos.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        informe.put("productosMasVendidos", productosMasVendidos);

        return informe;
    }


}

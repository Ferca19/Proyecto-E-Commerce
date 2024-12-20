package programacion.ejemplo.service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.ProductoDTO;
import programacion.ejemplo.Mapper.DetallePedidoMapper;
import programacion.ejemplo.Mapper.ProductoMapper;
import programacion.ejemplo.model.DetallePedido;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Producto;
import programacion.ejemplo.repository.DetallePedidoRepository;
import programacion.ejemplo.repository.PedidoRepository;
import org.springframework.transaction.annotation.Transactional;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Subcategoria;
import programacion.ejemplo.model.Marca;
import programacion.ejemplo.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class DetallePedidoService implements IDetallePedidoService {

    private static final Logger logger = LoggerFactory.getLogger(DetallePedidoService.class);

    @Autowired
    private DetallePedidoRepository modelRepository;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ProductoMapper productoMapper;
    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public double crearDetallePedido(Pedido pedido, List<DetallePedidoDTO> detallesPedidoDTO) {
        double importeTotal = 0.0;
        List<DetallePedido> listaDetalles = new ArrayList<>(); // Crear una lista de detalles

        // Crear los detalles del pedido y calcular los subtotales
        for (DetallePedidoDTO detallePedidoDTO : detallesPedidoDTO) {
            ProductoDTO productoDTO = productoService.obtenerPorId(detallePedidoDTO.getProductoId());

            // Verificar si el producto existe
            if (productoDTO == null || productoRepository.findByIdAndEliminado(detallePedidoDTO.getProductoId(), 0) == null) {
                throw new IllegalArgumentException("Producto no encontrado con ID: " + detallePedidoDTO.getProductoId());
            }

            // Verificar que la cantidad no sea cero o negativa
            if (detallePedidoDTO.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad del producto no puede ser 0 o negativa");
            }

            // Verificar si el stock del producto es suficiente
            if (productoDTO.getStock() < detallePedidoDTO.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto con ID: " + detallePedidoDTO.getProductoId());
            }


            productoService.ajustarInventarioProducto(detallePedidoDTO.getProductoId(),detallePedidoDTO.getCantidad(),1);

            // Crear el detalle del pedido
            DetallePedido detallePedido = DetallePedidoMapper.toEntity(detallePedidoDTO);
            detallePedido.setCantidad(detallePedidoDTO.getCantidad());
            double subtotal = productoDTO.getPrecio() * detallePedido.getCantidad();
            detallePedido.setSubtotal(subtotal);
            detallePedido.setPedido(pedido);
            detallePedido.setProducto(productoMapper.toEntity(productoDTO, null, null, null));

            modelRepository.save(detallePedido);
            listaDetalles.add(detallePedido); // Agregar detalle a la lista

            importeTotal += subtotal;
        }

        // Asignar la lista de detalles al pedido
        pedido.setDetallesPedido(listaDetalles);

        return importeTotal;
    }

    @Override
    public List<DetallePedido> obtenerTodosDetalles() {
        // Retorna todos los detalles de pedido
        return modelRepository.findAll();
    }





}

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

    @Transactional
    public double crearDetallePedido(Pedido pedido, List<DetallePedidoDTO> detallesPedidoDTO) {
        double importeTotal = 0.0;
        List<DetallePedido> listaDetalles = new ArrayList<>(); // Crear una lista de detalles

        // Crear los detalles del pedido y calcular los subtotales
        for (DetallePedidoDTO detallePedidoDTO : detallesPedidoDTO) {
            ProductoDTO productoDTO = productoService.obtenerPorId(detallePedidoDTO.getProductoId());

            if (productoDTO == null) {
                throw new EntityNotFoundException("Producto no encontrado con ID: " + detallePedidoDTO.getProductoId());
            }

            // Verificar si el stock del producto es suficiente
            if (productoDTO.getStock() < detallePedidoDTO.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto con ID: " + detallePedidoDTO.getProductoId());
            }

            // Restar la cantidad del stock
            productoDTO.setStock(productoDTO.getStock() - detallePedidoDTO.getCantidad());
            // Aquí actualiza el producto directamente en la base de datos si es necesario

            // Obtener las entidades necesarias para el mapeo
            Categoria categoria = productoService.obtenerCategoria(productoDTO.getCategoriaId()); // Método para obtener la categoría
            Subcategoria subcategoria = productoService.obtenerSubcategoria(productoDTO.getSubcategoriaId()); // Método para obtener la subcategoría
            Marca marca = productoService.obtenerMarca(productoDTO.getMarcaId());

            productoService.actualizarStockProducto(productoMapper.toEntity(productoDTO, categoria, subcategoria, marca)); // Guardar el producto con el nuevo stock

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





}

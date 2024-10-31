package programacion.ejemplo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import programacion.ejemplo.DTO.DetalleAjusteInventarioDTO;
import programacion.ejemplo.model.AjusteInventario;
import programacion.ejemplo.model.DetalleAjusteInventario;
import programacion.ejemplo.repository.AjusteInventarioRepository;
import programacion.ejemplo.repository.CategoriaRepository;
import programacion.ejemplo.repository.DetalleAjusteInventarioRepository;

@Service
public class DetalleAjusteInventarioService implements IDetalleAjusteInventarioService{

    @Autowired
    private IProductoService productoService;

    private final DetalleAjusteInventarioRepository modelRepository;

    // Constructor para inyección de dependencias
    public DetalleAjusteInventarioService(DetalleAjusteInventarioRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    // Método para ajustar el inventario del producto a través de DetalleAjusteInventarioService
    @Transactional
    public DetalleAjusteInventario realizarAjusteInventario(DetalleAjusteInventarioDTO detalleDTO, int tipoAjuste, AjusteInventario ajusteInventario) {
        // Ajusta el inventario del producto
        productoService.ajustarInventarioProducto(detalleDTO.getProductoId(), detalleDTO.getCantidadAjustada(), tipoAjuste);

        // Crear el detalle de ajuste de inventario a partir del DTO
        DetalleAjusteInventario detalle = new DetalleAjusteInventario();
        detalle.setAjusteInventario(ajusteInventario); // Asigna el ajuste de inventario
        detalle.setProducto(productoService.obtenerObjetoPorId(detalleDTO.getProductoId()));
        detalle.setCantidadAjustada(detalleDTO.getCantidadAjustada());
        detalle.setEliminado(DetalleAjusteInventario.NO);

        // Guardar el detalle en la base de datos
        return modelRepository.save(detalle);
    }
}

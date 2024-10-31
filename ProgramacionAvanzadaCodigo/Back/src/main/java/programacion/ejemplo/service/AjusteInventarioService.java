package programacion.ejemplo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import programacion.ejemplo.DTO.AjusteInventarioDTO;
import programacion.ejemplo.DTO.DetalleAjusteInventarioDTO;
import programacion.ejemplo.Mapper.AjusteInventarioMapper;
import programacion.ejemplo.model.AjusteInventario;
import programacion.ejemplo.model.DetalleAjusteInventario;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.AjusteInventarioRepository;
import programacion.ejemplo.repository.CategoriaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class AjusteInventarioService implements IAjusteInventarioService {

    private static final Logger logger = LoggerFactory.getLogger(AjusteInventarioService.class);

    @Autowired
    private IDetalleAjusteInventarioService detalleAjusteInventarioService;

    private final AjusteInventarioRepository modelRepository;

    // Constructor para inyección de dependencias
    public AjusteInventarioService(AjusteInventarioRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Transactional
    public void realizarAjusteInventario(Usuario usuario, AjusteInventarioDTO ajusteInventarioDTO) {

        // Crear la entidad AjusteInventario a partir del DTO y asignar el usuario
        AjusteInventario ajusteInventario = AjusteInventarioMapper.toEntity(ajusteInventarioDTO);
        ajusteInventario.setUsuario(usuario);

        // Asignar la fecha actual
        ajusteInventario.setFecha(new Date()); // Establece la fecha a la fecha actual


        // Crear y asociar los detalles al ajuste guardado
        List<DetalleAjusteInventario> detalles = new ArrayList<>();

        ajusteInventario.setDetalles(detalles);

        // Guardar AjusteInventario para obtener el ID
        AjusteInventario ajusteGuardado = modelRepository.save(ajusteInventario);

        for (DetalleAjusteInventarioDTO detalleDTO : ajusteInventarioDTO.getDetalles()) {
            DetalleAjusteInventario detalle = detalleAjusteInventarioService.realizarAjusteInventario(detalleDTO, ajusteGuardado.getTipoAjuste(), ajusteGuardado);
            detalles.add(detalle);
        }

        // Asignar los detalles al ajuste guardado y actualizar en la base de datos
        ajusteGuardado.setDetalles(detalles);
        modelRepository.save(ajusteGuardado);
    }

}

package programacion.ejemplo.service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.DTO.EstadoDTO;
import programacion.ejemplo.Mapper.CategoriaMapper;
import programacion.ejemplo.Mapper.EstadoMapper;
import programacion.ejemplo.exception.EntidadDuplicadaException;
import programacion.ejemplo.exception.EntidadFormatoInvalidoException;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.repository.CategoriaRepository;
import programacion.ejemplo.repository.EstadoRepository;

import java.util.List;


@Service

public class EstadoService implements IEstadoService {

    private static final Logger logger = LoggerFactory.getLogger(EstadoService.class);

    @Autowired
    private EstadoRepository modelRepository;

    @Autowired
    @Lazy
    private IPedidoService pedidoService;

    @Override
    public List<EstadoDTO> listar() {
        List<Estado> categorias = modelRepository.findByEliminado(Estado.NO);
        return categorias.stream().map(EstadoMapper::toDTO).toList();
    }

    @Override
    public Estado buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public EstadoDTO guardar(EstadoDTO modelDTO) {
        validarEstado(modelDTO);
        Estado model = EstadoMapper.toEntity(modelDTO);
        return EstadoMapper.toDTO(modelRepository.save(model));
    }


    @Override
    public void eliminar(Integer estadoId) {

        Estado estado = modelRepository.findById(estadoId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        boolean tienePedidos = pedidoService.existePorEstadoId(estadoId);
        if (tienePedidos) {
            throw new RuntimeException("El estado no puede eliminarse porque está asociado a uno o más pedidos.");
        }

        if (estado.getEliminado() == Estado.SI) {
            throw new RuntimeException("El estado ya está eliminado.");
        }

        estado.asEliminar();

        modelRepository.save(estado);
    }


    @Override
    public List<Estado> listarEstadosEliminados() {
        return modelRepository.findAllByEliminado(Estado.SI);
    }

    @Override
    public Estado recuperarEstadoEliminado(Integer id) {
        Estado estado = modelRepository.findByIdAndEliminado(id, Estado.SI);
        if (estado != null) {
            estado.setEliminado(Estado.NO);
            modelRepository.save(estado);
        }
        return estado;
    }

    @Override
    public Estado actualizarEstado(Integer id, Estado estado) {
        return modelRepository.findById(id)
                .filter(existingEstado -> existingEstado.getEliminado() == Estado.NO)
                .map(existingEstado -> {

                    if (estado.getNombre() != null) {
                        validarEstado(EstadoMapper.toDTO(estado));
                        existingEstado.setNombre(estado.getNombre());
                    }


                    if (estado.getDescripcion() != null) {
                        existingEstado.setDescripcion(estado.getDescripcion());
                    }

                    return modelRepository.save(existingEstado);
                })
                .orElse(null);
    }

    // Método para obtener el estado inicial (por ejemplo, "Pendiente")
    public Estado obtenerEstadoInicial() {
        return modelRepository.findById(1) // Asumiendo que el ID del estado inicial es 1
                .orElseThrow(() -> new EntityNotFoundException("Estado inicial no encontrado con ID: 1"));
    }

    private void validarEstado(EstadoDTO modelDTO) {
        // Verificar si el estado ya existe
        if (modelRepository.existsByNombreIgnoreCase(modelDTO.getNombre())) {
            throw new EntidadDuplicadaException("El estado ya existe.");
        }

        // Verificar espacios en blanco
        String nombre = modelDTO.getNombre().trim();
        if (nombre.isEmpty()) {
            throw new EntidadFormatoInvalidoException("El nombre del estado no puede estar vacío.");
        }

        // Verificar espacios al principio o al final
        if (!modelDTO.getNombre().equals(nombre)) {
            throw new EntidadFormatoInvalidoException("El nombre del estado no puede tener espacios al principio o al final.");
        }

        // Convertir a formato correcto (primer letra mayúscula)
        modelDTO.setNombre(capitalizar(nombre));
    }

    private String capitalizar(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return nombre;
        }
        return nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
    }
}

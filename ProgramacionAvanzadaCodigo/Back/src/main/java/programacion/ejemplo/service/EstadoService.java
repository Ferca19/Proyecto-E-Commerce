package programacion.ejemplo.service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.DTO.EstadoDTO;
import programacion.ejemplo.Mapper.CategoriaMapper;
import programacion.ejemplo.Mapper.EstadoMapper;
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
        Estado model = EstadoMapper.toEntity(modelDTO);
        return EstadoMapper.toDTO(modelRepository.save(model));
    }


    @Override
    public void eliminar(Integer estadoId) {

        Estado estado = modelRepository.findById(estadoId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

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
}

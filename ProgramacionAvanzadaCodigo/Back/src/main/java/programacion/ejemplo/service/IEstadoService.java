package programacion.ejemplo.service;

import programacion.ejemplo.DTO.EstadoDTO;
import programacion.ejemplo.model.Estado;

import java.util.List;

public interface IEstadoService {
    List<EstadoDTO> listar();

    Estado buscarPorId(Integer id);

    EstadoDTO guardar(EstadoDTO model);

    Estado actualizarEstado (Integer id,Estado model);

    void eliminar(Integer Estado);

    List<Estado> listarEstadosEliminados();

    Estado recuperarEstadoEliminado(Integer id);

    Estado obtenerEstadoInicial();
}

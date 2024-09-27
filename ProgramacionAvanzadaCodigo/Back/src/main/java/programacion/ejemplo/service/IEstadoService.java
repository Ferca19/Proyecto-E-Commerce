package programacion.ejemplo.service;

import programacion.ejemplo.DTO.EstadoDTO;
import programacion.ejemplo.model.Estado;

import java.util.List;

public interface IEstadoService {
    public List<EstadoDTO> listar();

    public Estado buscarPorId(Integer id);

    public EstadoDTO guardar(EstadoDTO model);

    public Estado actualizarEstado (Integer id,Estado model);

    public void eliminar(Integer Estado);

    List<Estado> listarEstadosEliminados();

    Estado recuperarEstadoEliminado(Integer id);

    Estado obtenerEstadoInicial();
}

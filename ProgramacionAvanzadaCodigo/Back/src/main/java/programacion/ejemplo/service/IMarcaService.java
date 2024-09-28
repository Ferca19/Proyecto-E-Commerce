package programacion.ejemplo.service;

import programacion.ejemplo.DTO.MarcaDTO;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Marca;

import java.util.List;
import java.util.Optional;

public interface IMarcaService {

    public Marca actualizarMarca(Integer id, Marca marca);

    public List<MarcaDTO> listar();

    public Marca buscarPorId(Integer id);

    public MarcaDTO guardar(MarcaDTO model);

    public void eliminar(Integer Marca);

    List<Marca> listarMarcasEliminadas();

    Marca recuperarMarcaEliminada(Integer id);

    Optional<Marca> obtenerPorId(Integer id);
}

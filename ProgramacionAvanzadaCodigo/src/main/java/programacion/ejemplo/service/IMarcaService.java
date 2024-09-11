package programacion.ejemplo.service;

import programacion.ejemplo.DTO.MarcaDTO;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Marca;

import java.util.List;

public interface IMarcaService {

    public Marca actualizarMarca(Integer id, Marca marca);

    public List<MarcaDTO> listar();

    public Marca buscarPorId(Integer id);

    public MarcaDTO guardar(MarcaDTO model);

    public void eliminar(Integer Marca);

    // Método para listar todas las marcas eliminadas
    List<Marca> listarMarcasEliminadas();

    // Método para recuperar una marca eliminada por ID
    Marca recuperarMarcaEliminada(Integer id);
}

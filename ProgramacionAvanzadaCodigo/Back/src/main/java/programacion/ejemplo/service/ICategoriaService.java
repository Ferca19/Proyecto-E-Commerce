package programacion.ejemplo.service;


import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {

    public List<CategoriaDTO> listar();

    public Categoria buscarPorId(Integer id);

    public CategoriaDTO guardar(CategoriaDTO model);

    public Categoria actualizarCategoria (Integer id,Categoria model);

    public void eliminar(Integer Categoria);
    public void eliminarFisicamente(Integer Categoria);

    List<Categoria> listarCategoriasEliminadas();

    Categoria recuperarCategoriaEliminada(Integer id);

    Optional<Categoria> obtenerPorId(Integer id);
}
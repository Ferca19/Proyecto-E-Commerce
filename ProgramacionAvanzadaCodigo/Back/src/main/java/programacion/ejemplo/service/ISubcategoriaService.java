package programacion.ejemplo.service;


import programacion.ejemplo.DTO.SubcategoriaDTO;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Subcategoria;

import java.util.List;
import java.util.Optional;

public interface ISubcategoriaService {

    public List<SubcategoriaDTO> listar();

    public Subcategoria buscarPorId(Integer id);

    public SubcategoriaDTO guardar(SubcategoriaDTO model);

    public Subcategoria actualizarSubcategoria (Integer id,Subcategoria model);

    public void eliminar(Integer Subcategoria);

    List<Subcategoria> listarSubcategoriasEliminadas();

    Subcategoria recuperarSubcategoriaEliminada(Integer id);

    Optional<Subcategoria> obtenerPorId(Integer id);
}

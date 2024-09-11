package programacion.ejemplo.service;


import programacion.ejemplo.DTO.SubcategoriaDTO;
import programacion.ejemplo.model.Subcategoria;

import java.util.List;

public interface ISubcategoriaService {

    public List<SubcategoriaDTO> listar();

    public Subcategoria buscarPorId(Integer id);

    public SubcategoriaDTO guardar(SubcategoriaDTO model);

    public Subcategoria actualizarSubcategoria (Integer id,Subcategoria model);

    public void eliminar(Integer Subcategoria);

    // Método para listar todas las subcategorías eliminadas
    List<Subcategoria> listarSubcategoriasEliminadas();

    // Método para recuperar una subcategoría eliminada por ID
    Subcategoria recuperarSubcategoriaEliminada(Integer id);
}

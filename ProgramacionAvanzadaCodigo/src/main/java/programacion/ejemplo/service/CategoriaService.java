package programacion.ejemplo.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.Mapper.CategoriaMapper;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.repository.CategoriaRepository;
import programacion.ejemplo.repository.ProductoRepository;


import java.util.List;

@Service

public class CategoriaService implements ICategoriaService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaRepository modelRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public List<CategoriaDTO> listar() {
        List<Categoria> categorias = modelRepository.findByEstado(Categoria.COMUN);
        return categorias.stream().map(CategoriaMapper::toDTO).toList();
    }

    @Override
    public Categoria buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public CategoriaDTO guardar(CategoriaDTO modelDTO) {
        Categoria model = CategoriaMapper.toEntity(modelDTO);
        return CategoriaMapper.toDTO(modelRepository.save(model));
    }


    @Override
    public void eliminar(Integer categoriaId) {
        // Verificar si la categoría está asociada a algún producto
        boolean tieneProductos = productoRepository.existsByCategoriaId(categoriaId);
        if (tieneProductos) {
            throw new RuntimeException("La categoría no puede eliminarse porque está asociada a uno o más productos.");
        }

        // Obtener la categoría por ID
        Categoria categoria = modelRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Verificar si la categoría ya está eliminada
        if (categoria.getEstado() == Categoria.ELIMINADO) {
            throw new RuntimeException("La categoría ya está eliminada.");
        }

        // Cambiar el estado a eliminado
        categoria.asEliminar();

        // Guardar la categoría con el nuevo estado
        modelRepository.save(categoria);
    }

    // Método para listar todas las categorías eliminadas
    public List<Categoria> listarCategoriasEliminadas() {
        return categoriaRepository.findAllByEstado(Categoria.ELIMINADO);
    }

    // Método para recuperar una categoría eliminada por ID
    public Categoria recuperarCategoriaEliminada(Integer id) {
        Categoria categoria = categoriaRepository.findByIdAndEstado(id, Categoria.ELIMINADO);
        if (categoria != null) {
            categoria.setEstado(Categoria.COMUN); // Cambia el estado de la categoría a común
            categoriaRepository.save(categoria); // Guarda los cambios
        }
        return categoria;
    }

    @Override
    public Categoria actualizarCategoria(Integer id, Categoria categoria) {
        return modelRepository.findById(id)
                .map(existingCategoria -> {
                    // Verifica si el nombre es nulo, si no lo es, actualiza
                    if (categoria.getNombre() != null) {
                        existingCategoria.setNombre(categoria.getNombre());
                    }

                    // Verifica si la descripción es nula, si no lo es, actualiza
                    if (categoria.getDescripcion() != null) {
                        existingCategoria.setDescripcion(categoria.getDescripcion());
                    }

                    return modelRepository.save(existingCategoria);
                })
                .orElse(null);
    }
}

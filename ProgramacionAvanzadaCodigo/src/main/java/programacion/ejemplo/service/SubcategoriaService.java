package programacion.ejemplo.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import programacion.ejemplo.DTO.SubcategoriaDTO;
import programacion.ejemplo.Mapper.SubcategoriaMapper;
import programacion.ejemplo.model.Subcategoria;
import programacion.ejemplo.repository.SubcategoriaRepository;
import programacion.ejemplo.repository.ProductoRepository;


import java.util.List;

@Service

public class SubcategoriaService implements ISubcategoriaService {

    private static final Logger logger = LoggerFactory.getLogger(SubcategoriaService.class);


    @Autowired
    private SubcategoriaRepository modelRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public List<SubcategoriaDTO> listar() {
        List<Subcategoria> subcategorias = modelRepository.findByEliminado(Subcategoria.NO);
        return subcategorias.stream().map(SubcategoriaMapper::toDTO).toList();
    }

    @Override
    public Subcategoria buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public SubcategoriaDTO guardar(SubcategoriaDTO modelDTO) {
        Subcategoria model = SubcategoriaMapper.toEntity(modelDTO);
        return SubcategoriaMapper.toDTO(modelRepository.save(model));
    }


    @Override
    public void eliminar(Integer subcategoriaId) {
        // Verificar si la categoría está asociada a algún producto
        boolean tieneProductos = productoRepository.existsBySubcategoriaId(subcategoriaId);
        if (tieneProductos) {
            throw new RuntimeException("La subcategoría no puede eliminarse porque está asociada a uno o más productos.");
        }

        // Obtener la subcategoría por ID
        Subcategoria subcategoria = modelRepository.findById(subcategoriaId)
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        // Verificar si la subcategoría ya está eliminada
        if (subcategoria.getEliminado() == Subcategoria.SI) {
            throw new RuntimeException("La categoría ya está eliminada.");
        }

        // Cambiar el estado a eliminado
        subcategoria.asEliminar();

        // Guardar la subcategoría con el nuevo estado
        modelRepository.save(subcategoria);
    }

    // Método para listar todas las subcategorías eliminadas
    @Override
    public List<Subcategoria> listarSubcategoriasEliminadas() {
        return modelRepository.findAllByEliminado(Subcategoria.SI);
    }

    // Método para recuperar una subcategoría eliminada por ID
    @Override
    public Subcategoria recuperarSubcategoriaEliminada(Integer id) {
        Subcategoria subcategoria = modelRepository.findByIdAndEliminado(id, Subcategoria.SI);
        if (subcategoria != null) {
            subcategoria.setEliminado(Subcategoria.NO); // Cambia el estado de la categoría a común
            modelRepository.save(subcategoria); // Guarda los cambios
        }
        return subcategoria;
    }

    @Override
    public Subcategoria actualizarSubcategoria(Integer id, Subcategoria subcategoria) {
        return modelRepository.findById(id)
                .filter(existingSubcategoria -> existingSubcategoria.getEliminado() == Subcategoria.NO) // Verifica que la subcategoría no esté eliminada
                .map(existingSubcategoria -> {
                    // Verifica si el nombre es nulo, si no lo es, actualiza
                    if (subcategoria.getNombre() != null) {
                        existingSubcategoria.setNombre(subcategoria.getNombre());
                    }

                    // Verifica si la descripción es nula, si no lo es, actualiza
                    if (subcategoria.getDescripcion() != null) {
                        existingSubcategoria.setDescripcion(subcategoria.getDescripcion());
                    }

                    return modelRepository.save(existingSubcategoria);
                })
                .orElse(null);
    }

}


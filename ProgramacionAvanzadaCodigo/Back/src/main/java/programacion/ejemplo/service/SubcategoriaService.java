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

        Subcategoria subcategoria = modelRepository.findById(subcategoriaId)
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        if (subcategoria.getEliminado() == Subcategoria.SI) {
            throw new RuntimeException("La categoría ya está eliminada.");
        }

        subcategoria.asEliminar();

        modelRepository.save(subcategoria);
    }

    @Override
    public List<Subcategoria> listarSubcategoriasEliminadas() {
        return modelRepository.findAllByEliminado(Subcategoria.SI);
    }

    @Override
    public Subcategoria recuperarSubcategoriaEliminada(Integer id) {
        Subcategoria subcategoria = modelRepository.findByIdAndEliminado(id, Subcategoria.SI);
        if (subcategoria != null) {
            subcategoria.setEliminado(Subcategoria.NO);
            modelRepository.save(subcategoria);
        }
        return subcategoria;
    }

    @Override
    public Subcategoria actualizarSubcategoria(Integer id, Subcategoria subcategoria) {
        return modelRepository.findById(id)
                .filter(existingSubcategoria -> existingSubcategoria.getEliminado() == Subcategoria.NO)
                .map(existingSubcategoria -> {
                    if (subcategoria.getNombre() != null) {
                        existingSubcategoria.setNombre(subcategoria.getNombre());
                    }

                    if (subcategoria.getDescripcion() != null) {
                        existingSubcategoria.setDescripcion(subcategoria.getDescripcion());
                    }

                    return modelRepository.save(existingSubcategoria);
                })
                .orElse(null);
    }

}

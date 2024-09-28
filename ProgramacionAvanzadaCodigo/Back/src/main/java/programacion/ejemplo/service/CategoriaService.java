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
import java.util.Optional;

@Service

public class CategoriaService implements ICategoriaService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);


    @Autowired
    private CategoriaRepository modelRepository;

    @Autowired
    private IProductoService productoService;


    @Override
    public List<CategoriaDTO> listar() {
        List<Categoria> categorias = modelRepository.findByEliminado(Categoria.NO);
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

        boolean tieneProductos = productoService.existePorCategoriaId(categoriaId);
        if (tieneProductos) {
            throw new RuntimeException("La categoría no puede eliminarse porque está asociada a uno o más productos.");
        }


        Categoria categoria = modelRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));


        if (categoria.getEliminado() == Categoria.SI) {
            throw new RuntimeException("La categoría ya está eliminada.");
        }

        categoria.asEliminar();

        modelRepository.save(categoria);
    }


    @Override
    public List<Categoria> listarCategoriasEliminadas() {
        return modelRepository.findAllByEliminado(Categoria.SI);
    }

    @Override
    public Categoria recuperarCategoriaEliminada(Integer id) {
        Categoria categoria = modelRepository.findByIdAndEliminado(id, Categoria.SI);
        if (categoria != null) {
            categoria.setEliminado(Categoria.NO);
            modelRepository.save(categoria);
        }
        return categoria;
    }

    @Override
    public Categoria actualizarCategoria(Integer id, Categoria categoria) {
        return modelRepository.findById(id)
                .filter(existingCategoria -> existingCategoria.getEliminado() == Categoria.NO)
                .map(existingCategoria -> {

                    if (categoria.getNombre() != null) {
                        existingCategoria.setNombre(categoria.getNombre());
                    }


                    if (categoria.getDescripcion() != null) {
                        existingCategoria.setDescripcion(categoria.getDescripcion());
                    }

                    return modelRepository.save(existingCategoria);
                })
                .orElse(null);
    }


    public Optional<Categoria> obtenerPorId(Integer id) {
        return modelRepository.findById(id);
    }

}

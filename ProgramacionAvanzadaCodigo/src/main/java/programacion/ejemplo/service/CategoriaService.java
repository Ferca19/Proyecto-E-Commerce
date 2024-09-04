package programacion.ejemplo.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.Mapper.CategoriaMapper;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.repository.CategoriaRepository;


import java.util.List;

@Service

public class CategoriaService implements ICategoriaService {
    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);
    @Autowired
    private CategoriaRepository modelRepository;

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
    public Categoria guardar(Categoria model) {
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Categoria model) {
        model.asEliminar();
        modelRepository.save(model);
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

                    // Verifica si el estado es distinto del valor por defecto
                    if (categoria.getEstado() != 0) { // O el valor que consideres por defecto
                        existingCategoria.setEstado(categoria.getEstado());
                    }

                    return modelRepository.save(existingCategoria);
                })
                .orElse(null);
    }
}

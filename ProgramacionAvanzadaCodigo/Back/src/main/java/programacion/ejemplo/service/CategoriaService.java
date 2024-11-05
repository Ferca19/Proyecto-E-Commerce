package programacion.ejemplo.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.Mapper.CategoriaMapper;
import programacion.ejemplo.exception.EntidadDuplicadaException;
import programacion.ejemplo.exception.EntidadFormatoInvalidoException;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.repository.CategoriaRepository;


import java.util.List;
import java.util.Optional;

@Service

public class CategoriaService implements ICategoriaService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);


    private final CategoriaRepository modelRepository;
    private final IProductoService productoService;

    // Constructor para inyección de dependencias
    public CategoriaService(CategoriaRepository modelRepository, IProductoService productoService) {
        this.modelRepository = modelRepository;
        this.productoService = productoService;
    }

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

        validarCategoria(modelDTO,modelDTO.getId());

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
    public void eliminarFisicamente(Integer categoriaId) {

        boolean tieneProductos = productoService.existePorCategoriaId(categoriaId);
        if (tieneProductos) {
            throw new RuntimeException("La categoría no puede eliminarse porque está asociada a uno o más productos.");
        }


        Categoria categoria = modelRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));


        if (categoria.getEliminado() == Categoria.SI) {
            throw new RuntimeException("La categoría ya está eliminada.");
        }

        // Aquí eliminamos la categoría de forma física
        modelRepository.deleteById(categoriaId); // Eliminar la categoría del repositorio
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

        Categoria categoriaExistente = modelRepository.findById(id)
                .filter(existingCategoria -> existingCategoria.getEliminado() == Categoria.NO)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada o ya eliminada."));


        // Validaciones
        validarCategoria(CategoriaMapper.toDTO(categoria), id); // Llamar al método de validación

        if (categoria.getNombre() != null) {
            categoriaExistente.setNombre(categoria.getNombre());
        }

        if (categoria.getDescripcion() != null) {
            categoriaExistente.setDescripcion(categoria.getDescripcion());
        }

        return modelRepository.save(categoriaExistente);
    }

    public Optional<Categoria> obtenerPorId(Integer id) {
        return modelRepository.findById(id);
    }

    private void validarCategoria(CategoriaDTO modelDTO, Integer categoriaId) {
        Optional<Categoria> categoriaExistente = modelRepository.findByNombreIgnoreCase(modelDTO.getNombre());

        // Si la categoría existe y no es la misma (si estamos actualizando)
        if (categoriaExistente.isPresent() && (categoriaId == null || !categoriaExistente.get().getId().equals(categoriaId))) {
            throw new EntidadDuplicadaException("La categoría ya existe con ese nombre.");
        }

        // Verificar espacios en blanco
        String nombre = modelDTO.getNombre().trim();
        if (nombre.isEmpty()) {
            throw new EntidadFormatoInvalidoException("El nombre de la categoría no puede estar vacío.");
        }

        // Verificar espacios al principio o al final
        if (!modelDTO.getNombre().equals(nombre)) {
            throw new EntidadFormatoInvalidoException("El nombre de la categoría no puede tener espacios al principio o al final.");
        }

        // Convertir a formato correcto (primer letra mayúscula)
        modelDTO.setNombre(capitalizar(nombre));
    }

    private String capitalizar(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return nombre;
        }
        return nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
    }

}

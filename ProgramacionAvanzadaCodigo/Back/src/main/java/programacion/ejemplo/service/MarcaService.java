package programacion.ejemplo.service;

import org.springframework.stereotype.Service;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.DTO.MarcaDTO;
import programacion.ejemplo.Mapper.CategoriaMapper;
import programacion.ejemplo.Mapper.EstadoMapper;
import programacion.ejemplo.Mapper.MarcaMapper;
import programacion.ejemplo.exception.EntidadDuplicadaException;
import programacion.ejemplo.exception.EntidadFormatoInvalidoException;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Marca;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import programacion.ejemplo.repository.MarcaRepository;
import programacion.ejemplo.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service

public class MarcaService implements IMarcaService {
    private static final Logger logger = LoggerFactory.getLogger(MarcaService.class);

    @Autowired
    private MarcaRepository modelRepository;

    @Autowired
    private IProductoService productoService;

    @Override
    public List<MarcaDTO> listar() {
        List<Marca> marcas = modelRepository.findByEliminado(Marca.NO);
        return marcas.stream().map(MarcaMapper::toDTO).toList();
    }

    @Override
    public Marca buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public MarcaDTO guardar(MarcaDTO modelDTO) {
        // Validar si existe una marca eliminada con el mismo nombre y recuperarla
        Marca marcaExistente = validarMarca(modelDTO, modelDTO.getId());

        // Si `validarMarca` encontró una marca eliminada, reutilizar esa entidad; si no, crear una nueva
        Marca model = marcaExistente != null ? marcaExistente : MarcaMapper.toEntity(modelDTO);

        // Actualizar los datos de la marca recuperada o de la nueva marca
        model.setDenominacion(modelDTO.getDenominacion());
        model.setObservaciones(modelDTO.getObservaciones());
        // Asignar otros atributos que necesites de `modelDTO` a `model`

        // Guardar y devolver la marca
        return MarcaMapper.toDTO(modelRepository.save(model));
    }

    @Override
    public Marca actualizarMarca(Integer id, Marca marca) {
        return modelRepository.findById(id)
                .filter(existingMarca -> existingMarca.getEliminado() == Marca.NO)
                .map(existingMarca -> {
                    // Validar la marca utilizando el DTO
                    validarMarca(MarcaMapper.toDTO(marca),id);

                    // Validar los campos que se están actualizando
                    if (marca.getDenominacion() != null) {
                        existingMarca.setDenominacion(marca.getDenominacion());
                    }

                    if (marca.getObservaciones() != null) {
                        existingMarca.setObservaciones(marca.getObservaciones());
                    }

                    return modelRepository.save(existingMarca);
                })
                .orElse(null);
    }

    @Override
    public void eliminar(Integer marcaId) {

        boolean tieneProductos = productoService.existePorMarcaId(marcaId);
        if (tieneProductos) {
            throw new RuntimeException("La marca no puede eliminarse porque está asociada a uno o más productos.");
        }


        Marca marca = modelRepository.findById(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));


        if (marca.getEliminado() == Marca.NO) {
            throw new RuntimeException("La marca ya está eliminada.");
        }

        marca.asEliminar();

        modelRepository.save(marca);
    }


    @Override
    public List<Marca> listarMarcasEliminadas() {
        return modelRepository.findAllByEliminado(Marca.SI);
    }

    @Override
    public Marca recuperarMarcaEliminada(Integer id) {
        Marca marca = modelRepository.findByIdAndEliminado(id, Marca.SI);
        if (marca != null) {
            marca.setEliminado(Marca.NO);
            modelRepository.save(marca);
        }
        return marca;
    }

    public Optional<Marca> obtenerPorId(Integer id) {
        return modelRepository.findById(id);
    }

    private Marca validarMarca(MarcaDTO modelDTO, Integer marcaId) {
        // Buscar marca existente con la misma denominación, ignorando mayúsculas
        Optional<Marca> marcaExistente = modelRepository.findByDenominacionIgnoreCase(modelDTO.getDenominacion());

        if (marcaExistente.isPresent()) {
            Marca marca = marcaExistente.get();

            // Verificar si la marca existente tiene el mismo ID
            if (!marca.getId().equals(marcaId)) {
                if (marca.getEliminado() == 1) {
                    // Recuperar y retornar la marca eliminada
                    return recuperarMarcaEliminada(marca.getId());
                } else {
                    throw new EntidadDuplicadaException("La marca ya existe con ese nombre.");
                }
            }
        }

        // Validaciones adicionales de formato de nombre
        if (modelDTO.getDenominacion() == null) {
            throw new EntidadFormatoInvalidoException("El nombre de la marca no puede estar vacío.");
        }

        String nombre = modelDTO.getDenominacion().trim();
        if (nombre.isEmpty()) {
            throw new EntidadFormatoInvalidoException("El nombre de la marca no puede estar vacío.");
        }
        if (!modelDTO.getDenominacion().equals(nombre)) {
            throw new EntidadFormatoInvalidoException("El nombre de la marca no puede tener espacios al principio o al final.");
        }

        modelDTO.setDenominacion(capitalizar(nombre));
        return null; // Retornar `null` si no hay una marca eliminada a recuperar
    }

    private String capitalizar(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return nombre;
        }
        return nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
    }
}

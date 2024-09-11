package programacion.ejemplo.service;

import org.springframework.stereotype.Service;
import programacion.ejemplo.DTO.MarcaDTO;
import programacion.ejemplo.Mapper.MarcaMapper;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Marca;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import programacion.ejemplo.repository.MarcaRepository;
import programacion.ejemplo.repository.ProductoRepository;

import java.util.List;

@Service

public class MarcaService implements IMarcaService {
    private static final Logger logger = LoggerFactory.getLogger(MarcaService.class);

    @Autowired
    private MarcaRepository modelRepository;

    @Autowired
    private ProductoRepository productoRepository;

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
        Marca model = MarcaMapper.toEntity(modelDTO);
        return MarcaMapper.toDTO(modelRepository.save(model));
    }

    @Override
    public Marca actualizarMarca(Integer id, Marca marca) {
        return modelRepository.findById(id)
                .filter(existingMarca -> existingMarca.getEliminado() == Marca.NO) // Verifica que la marca no esté eliminada
                .map(existingMarca -> {
                    // Verifica si la denominacion es nula, si no lo es, actualiza
                    if (marca.getDenominacion() != null) {
                        existingMarca.setDenominacion(marca.getDenominacion());
                    }

                    // Verifica si las observaciones son nulas, si no lo es, actualiza
                    if (marca.getObservaciones() != null) {
                        existingMarca.setObservaciones(marca.getObservaciones());
                    }

                    return modelRepository.save(existingMarca);
                })
                .orElse(null);
    }

    @Override
    public void eliminar(Integer marcaId) {
        // Verificar si la marca está asociada a algún producto
        boolean tieneProductos = productoRepository.existsByMarcaId(marcaId);
        if (tieneProductos) {
            throw new RuntimeException("La marca no puede eliminarse porque está asociada a uno o más productos.");
        }

        // Obtener la marca por ID
        Marca marca = modelRepository.findById(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        // Verificar si la marca ya está eliminada
        if (marca.getEliminado() == Marca.NO) {
            throw new RuntimeException("La marca ya está eliminada.");
        }


        // Cambiar el estado a eliminado
        marca.asEliminar();

        // Guardar la marca con el nuevo estado
        modelRepository.save(marca);
    }


    // Método para listar todas las marcas eliminadas
    @Override
    public List<Marca> listarMarcasEliminadas() {
        return modelRepository.findAllByEliminado(Marca.SI);
    }

    // Método para recuperar una marca eliminada por ID
    @Override
    public Marca recuperarMarcaEliminada(Integer id) {
        Marca marca = modelRepository.findByIdAndEliminado(id, Marca.SI);
        if (marca != null) {
            marca.setEliminado(Marca.NO); // Cambia el estado de la marca a común
            modelRepository.save(marca); // Guarda los cambios
        }
        return marca;
    }
}

package programacion.ejemplo.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import programacion.ejemplo.DTO.RolDTO;
import programacion.ejemplo.Mapper.RolMapper;
import programacion.ejemplo.exception.EntidadDuplicadaException;
import programacion.ejemplo.exception.EntidadFormatoInvalidoException;
import programacion.ejemplo.model.Rol;
import programacion.ejemplo.repository.RolRepository;


import java.util.List;
import java.util.Optional;

@Service

public class RolService implements IRolService {

    private static final Logger logger = LoggerFactory.getLogger(RolService.class);


    @Autowired
    private RolRepository modelRepository;



    @Override
    public List<RolDTO> listar() {
        List<Rol> roles = modelRepository.findByEliminado(Rol.NO);
        return roles.stream().map(RolMapper::toDTO).toList();
    }

    @Override
    public Rol buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public RolDTO guardar(RolDTO modelDTO) {

        validarRol(modelDTO);

        Rol model = RolMapper.toEntity(modelDTO);
        return RolMapper.toDTO(modelRepository.save(model));
    }


    @Override
    public void eliminar(Integer rolId) {


        Rol rol = modelRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));


        if (rol.getEliminado() == Rol.SI) {
            throw new RuntimeException("El rol ya está eliminado.");
        }

        rol.asEliminar();

        modelRepository.save(rol);
    }


    @Override
    public List<Rol> listarRolesEliminados() {
        return modelRepository.findAllByEliminado(Rol.SI);
    }

    @Override
    public Rol recuperarRolEliminado(Integer id) {
        Rol rol = modelRepository.findByIdAndEliminado(id, Rol.SI);
        if (rol != null) {
            rol.setEliminado(Rol.NO);
            modelRepository.save(rol);
        }
        return rol;
    }

    @Override
    public Rol actualizarRol(Integer id, Rol rol) {

        Rol rolExistente = modelRepository.findById(id)
                .filter(existingRol -> existingRol.getEliminado() == Rol.NO)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado o ya eliminado."));


        // Validaciones
        validarRol(RolMapper.toDTO(rol)); // Llamar al método de validación

        if (rol.getNombre() != null) {
            rolExistente.setNombre(rol.getNombre());
        }

        if (rol.getDescripcion() != null) {
            rolExistente.setDescripcion(rol.getDescripcion());
        }

        return modelRepository.save(rolExistente);
    }

    public Optional<Rol> obtenerPorId(Integer id) {
        return modelRepository.findById(id);
    }

    private void validarRol(RolDTO modelDTO) {
        // Verificar si la categoría ya existe
        if (modelRepository.existsByNombreIgnoreCase(modelDTO.getNombre())) { // Asumiendo que tienes este método en el repositorio
            throw new EntidadDuplicadaException("El rol ya existe.");
        }

        // Verificar espacios en blanco
        String nombre = modelDTO.getNombre().trim();
        if (nombre.isEmpty()) {
            throw new EntidadFormatoInvalidoException("El nombre del rol no puede estar vacío.");
        }

        // Verificar espacios al principio o al final
        if (!modelDTO.getNombre().equals(nombre)) {
            throw new EntidadFormatoInvalidoException("El nombre del rol no puede tener espacios al principio o al final.");
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

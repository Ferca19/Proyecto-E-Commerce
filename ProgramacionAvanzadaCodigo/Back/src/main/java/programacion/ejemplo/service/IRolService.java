package programacion.ejemplo.service;


import programacion.ejemplo.DTO.RolDTO;
import programacion.ejemplo.model.Rol;

import java.util.List;
import java.util.Optional;

public interface IRolService {

    public List<RolDTO> listar();

    public Rol buscarPorId(Integer id);

    public RolDTO guardar(RolDTO model);

    public Rol actualizarRol (Integer id,Rol model);

    public void eliminar(Integer Rol);

    List<Rol> listarRolesEliminados();

    Rol recuperarRolEliminado(Integer id);

    Optional<Rol> obtenerPorId(Integer id);
}
package programacion.ejemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.RolDTO;
import programacion.ejemplo.Mapper.RolMapper;
import programacion.ejemplo.exception.RecursoNoEncontradoExcepcion;
import programacion.ejemplo.model.Rol;
import programacion.ejemplo.service.IRolService;

import java.util.List;

@RestController
@RequestMapping("roles")
@CrossOrigin(value=" http://localhost:5173")

public class RolController {

    private static final Logger logger = LoggerFactory.getLogger(RolController.class);


    @Autowired
    private IRolService modelService;

    @GetMapping("/public")
    public List<RolDTO> getAll() {

        return modelService.listar();

    }

    @GetMapping("/public/{id}")
    public ResponseEntity<RolDTO> getPorId(@PathVariable Integer id){
        Rol model = modelService.buscarPorId(id);

        if(model == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        RolDTO modelDTO = RolMapper.toDTO(model);
        return ResponseEntity.ok(modelDTO);
    }



    @PostMapping("/admin")
    public RolDTO guardar(@RequestBody RolDTO model){
        return modelService.guardar(model);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Integer id, @RequestBody Rol rol) {
        Rol actualizarRol = modelService.actualizarRol(id, rol);
        if (actualizarRol != null) {
            return ResponseEntity.ok(actualizarRol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Rol model = modelService.buscarPorId(id);
        if (model == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }


        modelService.eliminar(id);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/admin/eliminadas")
    public List<Rol> listarRolesEliminados() {
        return modelService.listarRolesEliminados();
    }


    @PutMapping("/admin/recuperar/{id}")
    public ResponseEntity<Rol> recuperarRolEliminado(@PathVariable Integer id) {
        Rol rol = modelService.recuperarRolEliminado(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package programacion.ejemplo.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.EstadoDTO;
import programacion.ejemplo.DTO.SubcategoriaDTO;
import programacion.ejemplo.Mapper.EstadoMapper;
import programacion.ejemplo.Mapper.SubcategoriaMapper;
import programacion.ejemplo.exception.RecursoNoEncontradoExcepcion;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.model.Subcategoria;
import programacion.ejemplo.service.IEstadoService;
import programacion.ejemplo.service.ISubcategoriaService;

import java.util.List;

@RestController
@RequestMapping("estados")
@CrossOrigin(value=" http://localhost:5173")

public class EstadoController {

    private static final Logger logger = LoggerFactory.getLogger(EstadoController.class);


    @Autowired
    private IEstadoService modelService;

    @GetMapping
    public List<EstadoDTO> getAll() {
        logger.info("entra y trae todos los estados");
        return modelService.listar();

    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> getPorId(@PathVariable Integer id) {
        Estado model = modelService.buscarPorId(id);

        if (model == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        EstadoDTO modelDTO = EstadoMapper.toDTO(model);
        return ResponseEntity.ok(modelDTO);
    }


    @PostMapping
    public EstadoDTO guardar(@RequestBody EstadoDTO model) {
        return modelService.guardar(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> actualizarEstado(@PathVariable Integer id, @RequestBody Estado estado) {
        Estado actualizarEstado = modelService.actualizarEstado(id, estado);
        if (actualizarEstado != null) {
            return ResponseEntity.ok(actualizarEstado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Estado model = modelService.buscarPorId(id);
        if (model == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }

        modelService.eliminar(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/eliminados")
    public List<Estado> listarEstadosEliminadas() {
        return modelService.listarEstadosEliminados();
    }

    @PutMapping("/recuperar/{id}")
    public ResponseEntity<Estado> recuperarEstadoEliminada(@PathVariable Integer id) {
        Estado estado = modelService.recuperarEstadoEliminado(id);
        if (estado != null) {
            return ResponseEntity.ok(estado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
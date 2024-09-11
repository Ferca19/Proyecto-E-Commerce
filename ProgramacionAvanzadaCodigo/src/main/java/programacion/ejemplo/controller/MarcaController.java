package programacion.ejemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.MarcaDTO;
import programacion.ejemplo.Mapper.MarcaMapper;
import programacion.ejemplo.exception.RecursoNoEncontradoExcepcion;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.service.IMarcaService;
import programacion.ejemplo.model.Marca;

import java.util.List;

@RestController
@RequestMapping("marcas")
@CrossOrigin(value=" http://localhost:5173")

public class MarcaController {

    private static final Logger logger = LoggerFactory.getLogger(MarcaController.class);

    @Autowired
    private IMarcaService modelService;

    @GetMapping
    public List<MarcaDTO> getAll() {
        logger.info("entra y trae todas las marcas");
        return modelService.listar();

    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> getPorId(@PathVariable Integer id){
        Marca model = modelService.buscarPorId(id);

        if(model == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        MarcaDTO modelDTO = MarcaMapper.toDTO(model);
        return ResponseEntity.ok(modelDTO);
    }


    
    @PostMapping
    public MarcaDTO guardar(@RequestBody MarcaDTO model){
        return modelService.guardar(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizarMarca(@PathVariable Integer id, @RequestBody Marca marca) {
        Marca marcaActualizada = modelService.actualizarMarca(id, marca);
        if (marcaActualizada != null) {
            return ResponseEntity.ok(marcaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {


        Marca model = modelService.buscarPorId(id);
        if (model == null){
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }

        model.asEliminar();
        modelService.eliminar(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/eliminadas")
    public List<Marca> listarMarcasEliminadas() {
        return modelService.listarMarcasEliminadas();
    }


    @PutMapping("/recuperar/{id}")
    public ResponseEntity<Marca> recuperarMarcaEliminada(@PathVariable Integer id) {
        Marca marca = modelService.recuperarMarcaEliminada(id);
        if (marca != null) {
            return ResponseEntity.ok(marca);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package programacion.ejemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.Mapper.CategoriaMapper;
import programacion.ejemplo.exception.RecursoNoEncontradoExcepcion;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.service.ICategoriaService;

import java.util.List;

@RestController
@RequestMapping("categorias")
@CrossOrigin(value=" http://localhost:5173")

public class CategoriaController {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);


    @Autowired
    private ICategoriaService modelService;

    @GetMapping
    public List<CategoriaDTO> getAll() {
        logger.info("entra y trae todas las categorias");
        return modelService.listar();

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getPorId(@PathVariable Integer id){
        Categoria model = modelService.buscarPorId(id);

        if(model == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        CategoriaDTO modelDTO = CategoriaMapper.toDTO(model);
        return ResponseEntity.ok(modelDTO);
    }



    @PostMapping
    public CategoriaDTO guardar(@RequestBody CategoriaDTO model){
        return modelService.guardar(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        Categoria actualizarCategoria = modelService.actualizarCategoria(id, categoria);
        if (actualizarCategoria != null) {
            return ResponseEntity.ok(actualizarCategoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Categoria model = modelService.buscarPorId(id);
        if (model == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }


        modelService.eliminar(id);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/eliminadas")
    public List<Categoria> listarCategoriasEliminadas() {
        return modelService.listarCategoriasEliminadas();
    }


    @PutMapping("/recuperar/{id}")
    public ResponseEntity<Categoria> recuperarCategoriaEliminada(@PathVariable Integer id) {
        Categoria categoria = modelService.recuperarCategoriaEliminada(id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package programacion.ejemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.SubcategoriaDTO;
import programacion.ejemplo.Mapper.SubcategoriaMapper;
import programacion.ejemplo.exception.RecursoNoEncontradoExcepcion;
import programacion.ejemplo.model.Subcategoria;
import programacion.ejemplo.service.ISubcategoriaService;

import java.util.List;

@RestController
@RequestMapping("subcategorias")
@CrossOrigin(value="http://localhost:5173")

public class SubcategoriaController {

    private static final Logger logger = LoggerFactory.getLogger(SubcategoriaController.class);


    @Autowired
    private ISubcategoriaService modelService;

    @GetMapping("/public")
    public List<SubcategoriaDTO> getAll() {
        logger.info("entra y trae todas las categorias");
        return modelService.listar();

    }

    @GetMapping("/public/{id}")
    public ResponseEntity<SubcategoriaDTO> getPorId(@PathVariable Integer id){
        Subcategoria model = modelService.buscarPorId(id);

        if(model == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        SubcategoriaDTO modelDTO = SubcategoriaMapper.toDTO(model);
        return ResponseEntity.ok(modelDTO);
    }



    @PostMapping("/admin")
    public SubcategoriaDTO guardar(@RequestBody SubcategoriaDTO model){
        return modelService.guardar(model);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<Subcategoria> actualizarSubcategoria(@PathVariable Integer id, @RequestBody Subcategoria categoria) {
        Subcategoria actualizarSubcategoria = modelService.actualizarSubcategoria(id, categoria);
        if (actualizarSubcategoria != null) {
            return ResponseEntity.ok(actualizarSubcategoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Subcategoria model = modelService.buscarPorId(id);
        if (model == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }

        modelService.eliminar(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/eliminadas")
    public List<Subcategoria> listarSubcategoriasEliminadas() {
        return modelService.listarSubcategoriasEliminadas();
    }

    @PutMapping("/admin/recuperar/{id}")
    public ResponseEntity<Subcategoria> recuperarSubcategoriaEliminada(@PathVariable Integer id) {
        Subcategoria categoria = modelService.recuperarSubcategoriaEliminada(id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

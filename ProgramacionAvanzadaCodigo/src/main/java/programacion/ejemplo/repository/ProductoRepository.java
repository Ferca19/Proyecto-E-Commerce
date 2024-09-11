package programacion.ejemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.ejemplo.model.Categoria;
import programacion.ejemplo.model.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    boolean existsByCategoriaId(Integer categoriaId);

    boolean existsByMarcaId(Integer marcaId);

    boolean existsBySubcategoriaId(Integer subcategoriaId);

    List<Producto> findAllById(Iterable<Integer> ids);

    List<Producto> findByEliminado(int eliminado);

    // Método para listar productos eliminados
    List<Producto> findAllByEliminado(int eliminado);

    // Método para recuperar un producto eliminado por ID
    Producto findByIdAndEliminado(int id, int eliminado);
}

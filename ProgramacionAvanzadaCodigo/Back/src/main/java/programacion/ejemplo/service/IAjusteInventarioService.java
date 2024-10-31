package programacion.ejemplo.service;

import programacion.ejemplo.DTO.AjusteInventarioDTO;
import programacion.ejemplo.model.Usuario;

public interface IAjusteInventarioService {

    void realizarAjusteInventario(Usuario usuario, AjusteInventarioDTO ajusteInventarioDTO ) ;
}

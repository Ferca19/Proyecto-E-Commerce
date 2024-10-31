package programacion.ejemplo.service;

import programacion.ejemplo.DTO.DetalleAjusteInventarioDTO;
import programacion.ejemplo.model.AjusteInventario;
import programacion.ejemplo.model.DetalleAjusteInventario;

public interface IDetalleAjusteInventarioService {

    DetalleAjusteInventario realizarAjusteInventario(DetalleAjusteInventarioDTO detalleDTO, int tipoAjuste, AjusteInventario ajusteInventario);
}

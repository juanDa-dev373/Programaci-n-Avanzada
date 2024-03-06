package co.edu.uniquindio.proyecto.model.documents;

import co.edu.uniquindio.proyecto.model.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.model.entity.Ubicacion;
import co.edu.uniquindio.proyecto.model.entity.HistorialRevicion;
import co.edu.uniquindio.proyecto.model.entity.Horario;
import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;

import java.util.List;

public class Negocio {
    private Ubicacion ubicacion;
    private String nombre;
    private String descripcion;
    private List<Horario> horarios;
    private EstadoNegocio estado;
    private List<String> imagenes;
    private List<HistorialRevicion> historialReviciones;
    private String codigo;
    private String codigoCliente;
    private TipoNegocio tipoNegocio;
    private List<String> telefonos;
}

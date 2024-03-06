package org.example.proyecto.model.documents;

import org.example.proyecto.model.enums.TipoNegocio;
import org.example.proyecto.model.entity.Ubicacion;
import org.example.proyecto.model.entity.HistorialRevicion;
import org.example.proyecto.model.entity.Horario;
import org.example.proyecto.model.enums.EstadoNegocio;

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

package co.edu.uniquindio.proyecto.model.entity;

import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;

import java.time.LocalDateTime;

public class HistorialRevicion {
    private String descripcion;
    private EstadoNegocio estadoNegocio;
    private LocalDateTime fecha;
    private String codigoModerador;
}

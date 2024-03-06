package org.example.proyecto.model.entity;

import org.example.proyecto.model.enums.EstadoNegocio;

import java.time.LocalDateTime;

public class HistorialRevicion {
    private String descripcion;
    private EstadoNegocio estadoNegocio;
    private LocalDateTime fecha;
    private String codigoModerador;
}

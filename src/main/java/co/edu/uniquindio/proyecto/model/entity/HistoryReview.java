package co.edu.uniquindio.proyecto.model.entity;

import co.edu.uniquindio.proyecto.model.enums.StateBusiness;

import java.time.LocalDateTime;

public class HistoryReview {
    private String descripcion;
    private StateBusiness estadoNegocio;
    private LocalDateTime fecha;
    private String codigoModerador;
}

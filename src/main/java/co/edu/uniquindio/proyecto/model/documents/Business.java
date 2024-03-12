package co.edu.uniquindio.proyecto.model.documents;

import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import co.edu.uniquindio.proyecto.model.entity.Ubication;
import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.entity.Schedule;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;

import java.util.List;

public class Business {
    private Ubication ubicacion;
    private String nombre;
    private String descripcion;
    private List<Schedule> horarios;
    private StateBusiness estado;
    private List<String> imagenes;
    private List<HistoryReview> historialReviciones;
    private String codigo;
    private String codigoCliente;
    private TypeBusiness tipoNegocio;
    private List<String> telefonos;
}

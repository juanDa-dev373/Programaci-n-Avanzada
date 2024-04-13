package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.documents.Business;

import java.util.List;

public record ListBusinessDto(

    String id,
    String listName,
    List<BusinessDto> businesses

) {
}

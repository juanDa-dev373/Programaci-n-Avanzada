package co.edu.uniquindio.proyecto.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule extends HistoryReview {
    private String start;
    private String day;
    private String end;
}

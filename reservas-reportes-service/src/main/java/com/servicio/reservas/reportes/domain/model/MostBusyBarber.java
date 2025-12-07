package com.servicio.reservas.reportes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MostBusyBarber {
    private Long barberId;
    private String nameBarber;
    private Long totalReservations;
}

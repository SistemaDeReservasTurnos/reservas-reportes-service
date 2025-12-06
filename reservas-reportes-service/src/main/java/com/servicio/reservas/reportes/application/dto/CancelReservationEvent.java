package com.servicio.reservas.reportes.application.dto;

import lombok.Data;

@Data
public class CancelReservationEvent {
    private Long reservationId;
}

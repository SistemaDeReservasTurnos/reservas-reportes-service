package com.servicio.reservas.reportes.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationHistoryFilter {
    private Long userId;
    private Long serviceId;
    private Long barberId;
    private String status;
    private LocalDate startDate = LocalDate.now().minusMonths(1);
}

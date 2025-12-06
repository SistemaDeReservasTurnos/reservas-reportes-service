package com.servicio.reservas.reportes.domain.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportReservation {
    private Long id;
    private Long reservationId;
    private Long serviceId;
    private String nameService;
    private Long userId;
    private String nameUser;
    private Long barberId;
    private String nameBarber;
    private LocalDate date;
    private String status;
    private Double amount;
}

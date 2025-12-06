package com.servicio.reservas.reportes.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateReservationEvent {
    private Long id;
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


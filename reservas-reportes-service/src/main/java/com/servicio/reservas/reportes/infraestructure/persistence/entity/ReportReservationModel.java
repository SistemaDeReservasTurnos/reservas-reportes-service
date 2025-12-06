package com.servicio.reservas.reportes.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "report_reservations")
@Data
@NoArgsConstructor

public class ReportReservationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long reservationId;

    @Column
    private Long serviceId;

    @Column
    private String nameService;

    @Column
    private Long userId;

    @Column
    private String nameUser;

    @Column
    private Long barberId;

    @Column
    private String nameBarber;

    @Column
    private LocalDate date;

    @Column
    private String status;

    @Column
    private Double amount;
}

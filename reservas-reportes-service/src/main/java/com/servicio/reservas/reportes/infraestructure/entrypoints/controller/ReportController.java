package com.servicio.reservas.reportes.infraestructure.entrypoints.controller;


import com.servicio.reservas.reportes.application.services.ReportReservationService;
import com.servicio.reservas.reportes.domain.model.ReportReservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportReservationService reportReservationService;
    public ReportController(ReportReservationService reportReservationService) {
        this.reportReservationService = reportReservationService;
    }

    @GetMapping("/reservations-completed")
    public ResponseEntity<List<ReportReservation>> getReservationsCompletedForTime(
            @RequestParam String period
    ) {
        List<ReportReservation> reservations =  reportReservationService.getReservationsCompletedForTime(period);
        return ResponseEntity.ok().body(reservations);
    }
}

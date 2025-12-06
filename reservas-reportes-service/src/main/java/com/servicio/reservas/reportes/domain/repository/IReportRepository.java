package com.servicio.reservas.reportes.domain.repository;

import com.servicio.reservas.reportes.application.dto.CancelReservationEvent;
import com.servicio.reservas.reportes.application.dto.CompletedReservationEvent;
import com.servicio.reservas.reportes.domain.model.ReportReservation;
import com.servicio.reservas.reportes.infraestructure.persistence.entity.ReportReservationModel;

import java.time.LocalDate;
import java.util.List;

public interface IReportRepository {
    void saveReservation(ReportReservation reservation);
    void cancelReservation(CancelReservationEvent event);
    void completedReservation(CompletedReservationEvent event);
    List<ReportReservationModel> findCompletedByDate(LocalDate startDate);
}

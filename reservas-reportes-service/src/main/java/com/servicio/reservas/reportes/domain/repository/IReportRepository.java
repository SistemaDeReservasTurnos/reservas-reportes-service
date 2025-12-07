package com.servicio.reservas.reportes.domain.repository;

import com.servicio.reservas.reportes.application.dto.CancelReservationEvent;
import com.servicio.reservas.reportes.application.dto.CompletedReservationEvent;
import com.servicio.reservas.reportes.application.dto.ReservationHistoryFilter;
import com.servicio.reservas.reportes.domain.model.MostBusyBarber;
import com.servicio.reservas.reportes.domain.model.MostUsedService;
import com.servicio.reservas.reportes.domain.model.ReportReservation;

import java.time.LocalDate;
import java.util.List;

public interface IReportRepository {
    void saveReservation(ReportReservation reservation);
    void cancelReservation(CancelReservationEvent event);
    void completedReservation(CompletedReservationEvent event);
    List<ReportReservation> findCompletedByDate(LocalDate startDate);
    Double getTotalForRange(LocalDate start, LocalDate end);
    List<MostBusyBarber> findMostBusyBarbers(LocalDate start, LocalDate end);
    List<MostUsedService> findMostUsedServices(LocalDate start, LocalDate end);
    List<ReportReservation> getReservationHistory(ReservationHistoryFilter filter);
}

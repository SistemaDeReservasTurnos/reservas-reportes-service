package com.servicio.reservas.reportes.application.services;

import com.servicio.reservas.reportes.application.dto.CancelReservationEvent;
import com.servicio.reservas.reportes.application.dto.CompletedReservationEvent;
import com.servicio.reservas.reportes.application.dto.CreateReservationEvent;
import com.servicio.reservas.reportes.domain.model.ReportReservation;

import java.util.List;

public interface IReservationEventListener {
    void procesarReservaCreada(CreateReservationEvent reservationEvent);
    void procesarReservaCancelada(CancelReservationEvent resrvationEvent);
    void procesarReservaCompletada(CompletedReservationEvent reservationEvent);
    List<ReportReservation> getReservationsCompletedForTime(String period);
}

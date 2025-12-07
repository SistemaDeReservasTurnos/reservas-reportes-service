package com.servicio.reservas.reportes.application.services;

import com.servicio.reservas.reportes.application.dto.*;
import com.servicio.reservas.reportes.domain.enums.ReportPeriod;
import com.servicio.reservas.reportes.domain.model.ReportReservation;

import java.util.List;

public interface IReservationEventListener {
    void procesarReservaCreada(CreateReservationEvent reservationEvent);
    void procesarReservaCancelada(CancelReservationEvent reservationEvent);
    void procesarReservaCompletada(CompletedReservationEvent reservationEvent);
}

package com.servicio.reservas.reportes.application.mappers;

import com.servicio.reservas.reportes.application.dto.CreateReservationEvent;
import com.servicio.reservas.reportes.domain.model.ReportReservation;

public class CreateReservationMapper {

    public static ReportReservation toDomain(CreateReservationEvent event){

        ReportReservation reportReservation = new ReportReservation();
        reportReservation.setReservationId(event.getId());
        reportReservation.setServiceId(event.getServiceId());
        reportReservation.setNameService(event.getNameService());
        reportReservation.setUserId(event.getUserId());
        reportReservation.setNameUser(event.getNameUser());
        reportReservation.setBarberId(event.getBarberId());
        reportReservation.setNameBarber(event.getNameBarber());
        reportReservation.setDate(event.getDate());
        reportReservation.setStatus(event.getStatus());
        reportReservation.setAmount(event.getAmount());

        return reportReservation;
    }
}

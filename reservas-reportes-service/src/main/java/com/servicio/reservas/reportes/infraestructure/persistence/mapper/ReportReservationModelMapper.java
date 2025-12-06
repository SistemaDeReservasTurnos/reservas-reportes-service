package com.servicio.reservas.reportes.infraestructure.persistence.mapper;

import com.servicio.reservas.reportes.domain.model.ReportReservation;
import com.servicio.reservas.reportes.infraestructure.persistence.entity.ReportReservationModel;
import org.springframework.beans.BeanUtils;

public class ReportReservationModelMapper {

    public static ReportReservationModel toModel(ReportReservation reservation) {
        ReportReservationModel reservationModel = new ReportReservationModel();
        BeanUtils.copyProperties(reservation, reservationModel);
        return reservationModel;
    }

    public static ReportReservation toDomain(ReportReservationModel reservationModel) {
        ReportReservation reservation = new ReportReservation();
        BeanUtils.copyProperties(reservationModel, reservation);
        return reservation;
    }
}

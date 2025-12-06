package com.servicio.reservas.reportes.application.services;

import com.servicio.reservas.reportes.application.dto.CancelReservationEvent;
import com.servicio.reservas.reportes.application.dto.CompletedReservationEvent;
import com.servicio.reservas.reportes.application.dto.CreateReservationEvent;
import com.servicio.reservas.reportes.application.mappers.CreateReservationMapper;
import com.servicio.reservas.reportes.domain.model.ReportReservation;
import com.servicio.reservas.reportes.domain.repository.IReportRepository;
import com.servicio.reservas.reportes.infraestructure.persistence.entity.ReportReservationModel;
import com.servicio.reservas.reportes.infraestructure.persistence.mapper.ReportReservationModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportReservationService implements IReservationEventListener{

    private final IReportRepository repo;

    public ReportReservationService(IReportRepository repo) {
        this.repo = repo;
    }

    @Override
    public void procesarReservaCreada(CreateReservationEvent reservationEvent) {
        ReportReservation reservation = CreateReservationMapper.toDomain(reservationEvent);
        repo.saveReservation(reservation);
    }

    @Override
    public void procesarReservaCancelada(CancelReservationEvent event) {
        repo.cancelReservation(event);
    }

    @Override
    public void procesarReservaCompletada(CompletedReservationEvent event) {
        repo.completedReservation(event);
    }

    @Override
    public List<ReportReservation> getReservationsCompletedForTime(String period) {
        LocalDate startDate = switch (period.toLowerCase()) {
            case "week" -> LocalDate.now().minusDays(7);
            case "month" -> LocalDate.now().minusMonths(1);
            default -> throw new Error("Invalid period: " + period + ". Supported values are: 'week', 'month'");
        };

        List<ReportReservationModel> reservationsModel = repo.findCompletedByDate(startDate);
        return reservationsModel.stream().map(ReportReservationModelMapper::toDomain).toList();
    }
}

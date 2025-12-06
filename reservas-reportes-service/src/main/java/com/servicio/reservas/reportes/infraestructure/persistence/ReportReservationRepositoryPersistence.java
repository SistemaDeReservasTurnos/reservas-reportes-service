package com.servicio.reservas.reportes.infraestructure.persistence;

import com.servicio.reservas.reportes.application.dto.CancelReservationEvent;
import com.servicio.reservas.reportes.application.dto.CompletedReservationEvent;
import com.servicio.reservas.reportes.domain.model.ReportReservation;
import com.servicio.reservas.reportes.domain.repository.IReportRepository;
import com.servicio.reservas.reportes.infraestructure.persistence.entity.ReportReservationModel;
import com.servicio.reservas.reportes.infraestructure.persistence.mapper.ReportReservationModelMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ReportReservationRepositoryPersistence implements IReportRepository {

    private final SpringReportReservationRepository springReportReservationRepository;

    public ReportReservationRepositoryPersistence(SpringReportReservationRepository springReportReservationRepository) {
        this.springReportReservationRepository = springReportReservationRepository;
    }

    @Override
    public void saveReservation(ReportReservation reservation) {

        ReportReservationModel reservationModel = ReportReservationModelMapper.toModel(reservation);
        springReportReservationRepository.save(reservationModel);
    }

    @Override
    public void cancelReservation(CancelReservationEvent event) {
        springReportReservationRepository.findByReservationId(event.getReservationId())
                .ifPresentOrElse(report -> {
                    report.setStatus("CANCELED");
                    springReportReservationRepository.save(report);
                    System.out.println("Reserva " + event.getReservationId() + " actualizada a CANCELADA en reportes.");
                },() -> {
                    System.out.println("Reserva " + event.getReservationId() + " no encontrada");
                } );
    }

    @Override
    public void completedReservation(CompletedReservationEvent event) {
        springReportReservationRepository.findByReservationId(event.getReservationId())
                .ifPresentOrElse(report -> {
                    report.setStatus("COMPLETED");
                    springReportReservationRepository.save(report);
                    System.out.println("Reserva " + event.getReservationId() + " actualizada a COMPLETADA en reportes.");
                }, () -> {
                    System.out.println("Reserva " + event.getReservationId() + " no encontrada");
                });
    }

    @Override
    public List<ReportReservation> findCompletedByDate(LocalDate startDate) {
        List<ReportReservationModel> reservations = springReportReservationRepository.findCompletedStatusByDate(startDate);
        return reservations.stream().map(ReportReservationModelMapper::toDomain).toList();
    }

    @Override
    public Double getTotalForRange(LocalDate start, LocalDate end) {
        return springReportReservationRepository.getTotalByRange(start, end);
    }
}


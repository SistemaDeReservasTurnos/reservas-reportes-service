package com.servicio.reservas.reportes.application.services;

import com.servicio.reservas.reportes.application.dto.*;
import com.servicio.reservas.reportes.application.mappers.CreateReservationMapper;
import com.servicio.reservas.reportes.domain.enums.ReportPeriod;
import com.servicio.reservas.reportes.domain.enums.ReservationStatus;
import com.servicio.reservas.reportes.domain.model.MostBusyBarber;
import com.servicio.reservas.reportes.domain.model.MostUsedService;
import com.servicio.reservas.reportes.domain.model.ReportReservation;
import com.servicio.reservas.reportes.domain.repository.IReportRepository;
import com.servicio.reservas.reportes.infraestructure.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportReservationService implements IReservationEventListener {

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

    public List<ReportReservation> getReservationsCompletedForTime(String period) {
        LocalDate startDate = switch (period.toLowerCase()) {
            case "week" -> LocalDate.now().minusDays(7);
            case "month" -> LocalDate.now().minusMonths(1);
            default -> throw new IllegalArgumentException("Invalid period: " + period + ". Supported values are: 'week', 'month'");
        };

        return repo.findCompletedByDate(startDate);
    }

    public ReportTotalAmount getTotalAmountForTime(ReportPeriod period){
        LocalDate today = LocalDate.now();
        LocalDate currentStart;

        LocalDate previousEnd;
        LocalDate previousStart;

        switch(period){
            case WEEK:
                currentStart = today.minusDays(7);
                previousEnd = currentStart.minusDays(1);
                previousStart = previousEnd.minusDays(6);
                break;

            case MONTH:
                currentStart = today.minusMonths(1);
                previousEnd = currentStart.minusDays(1);
                previousStart = previousEnd.minusMonths(1).plusDays(1);
                break;

            default:
                throw new IllegalArgumentException("Invalid period: " + period + ". Supported values are: 'week', 'month'");
        }

        Double currentPeriodTotal = repo.getTotalForRange(currentStart, today);
        Double previousPeriodTotal = repo.getTotalForRange(previousStart, previousEnd);

        double variation = 0.0;
        if (previousPeriodTotal > 0){
            variation = ((currentPeriodTotal - previousPeriodTotal) / previousPeriodTotal) * 100;
        } else if (currentPeriodTotal > 0){
            variation = 100.0;
        }

        String trend = variation > 0 ? "positive"  : (variation < 0) ? "negative" : "neutral";

        ReportTotalAmount totalAmount = new ReportTotalAmount();
        totalAmount.setReport("Total Amount By Periods");

        totalAmount.setPeriod(new ReportTotalAmount.PeriodInfo(
                "Last " + period,
                currentStart,
                today
        ));

        ReportTotalAmount.FinancialData data = new ReportTotalAmount.FinancialData(
                currentPeriodTotal,
                previousPeriodTotal,
                Math.round(variation * 100.0) / 100.0,
                trend,
                "COP"
        );

        totalAmount.setData(data);

        return totalAmount;
    }

    public List<ReportReservation> getReservationHistory(ReservationHistoryFilter filter) {
        if (filter.getStatus() != null) {
            try {
                ReservationStatus.valueOf(filter.getStatus());
            } catch (IllegalArgumentException e) {
                throw new BusinessException("Invalid filter status: " + filter.getStatus() +
                        " allowed statuses are: 'COMPLETED', 'CANCELED', 'RESERVED'");
            }
        }
        return repo.getReservationHistory(filter);
    }

    public List<MostBusyBarber> getMostBusyBarbers(String period) {
        LocalDate today = LocalDate.now();
        LocalDate startDate;

        switch (period.toLowerCase()) {
            case "week" -> startDate = today.minusDays(7);
            case "month" -> startDate = today.minusMonths(1);
            default -> throw new IllegalArgumentException("Invalid period: " + period);
        }

        return repo.findMostBusyBarbers(startDate, today);
    }

    public List<MostUsedService> getMostUsedServices(String period) {
        LocalDate today = LocalDate.now();
        LocalDate startDate;

        switch (period.toLowerCase()) {
            case "week" -> startDate = today.minusDays(7);
            case "month" -> startDate = today.minusMonths(1);
            default -> throw new IllegalArgumentException("Invalid period: " + period);
        }

        return repo.findMostUsedServices(startDate, today);
    }


}
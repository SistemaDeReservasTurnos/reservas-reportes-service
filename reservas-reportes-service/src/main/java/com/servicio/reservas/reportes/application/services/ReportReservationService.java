package com.servicio.reservas.reportes.application.services;

import com.servicio.reservas.reportes.application.dto.CancelReservationEvent;
import com.servicio.reservas.reportes.application.dto.CompletedReservationEvent;
import com.servicio.reservas.reportes.application.dto.CreateReservationEvent;
import com.servicio.reservas.reportes.application.dto.ReportTotalAmount;
import com.servicio.reservas.reportes.application.mappers.CreateReservationMapper;
import com.servicio.reservas.reportes.domain.enums.ReportPeriod;
import com.servicio.reservas.reportes.domain.model.ReportReservation;
import com.servicio.reservas.reportes.domain.repository.IReportRepository;
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

    @Override
    public List<ReportReservation> getReservationsCompletedForTime(String period) {
        LocalDate startDate = switch (period.toLowerCase()) {
            case "week" -> LocalDate.now().minusDays(7);
            case "month" -> LocalDate.now().minusMonths(1);
            default -> throw new IllegalArgumentException("Invalid period: " + period + ". Supported values are: 'week', 'month'");
        };

        return repo.findCompletedByDate(startDate);
    }

    @Override
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

        String trend = variation >= 0 ? "positive"  : "negative";

        ReportTotalAmount totalAmount = new ReportTotalAmount();
        totalAmount.setReport("Total Amount By Periods");

        totalAmount.setPeriod(new ReportTotalAmount.PeriodInfo(
                "Last " + period,
                currentStart,
                today
        ));

        ReportTotalAmount.FinancialData data = new ReportTotalAmount.FinancialData();
        data.setCurrent_period_total(currentPeriodTotal);
        data.setPrevious_period_total(previousPeriodTotal);
        data.setPercentage_variation(Math.round(variation * 100.0) / 100.0);
        data.setTrends(trend);

        totalAmount.setData(data);

        return totalAmount;
    }
}
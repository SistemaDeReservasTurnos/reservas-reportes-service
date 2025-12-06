package com.servicio.reservas.reportes.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class ReportTotalAmount {
    private String report;
    private PeriodInfo period;
    private FinancialData data;

    @Data
    @AllArgsConstructor
    public static class PeriodInfo{
        private String type;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Data
    @AllArgsConstructor
    public static class FinancialData{
        private Double currentPeriodTotal;
        private Double previousPeriodTotal;
        private Double percentageVariation;
        private String trend;
        private String currency;
    }
}

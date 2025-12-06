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
    @NoArgsConstructor
    public static class FinancialData{
        private Double current_period_total;
        private Double previous_period_total;
        private Double percentage_variation;
        private String trends;
        private String currency = "COP";
    }
}

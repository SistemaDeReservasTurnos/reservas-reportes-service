package com.servicio.reservas.reportes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MostUsedService {
    private Long serviceId;
    private String nameService;
    private Long totalUsed;
}

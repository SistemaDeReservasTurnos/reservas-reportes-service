package com.servicio.reservas.reportes.infraestructure.entrypoints.messaging;

import com.servicio.reservas.reportes.application.dto.CancelReservationEvent;
import com.servicio.reservas.reportes.application.dto.CompletedReservationEvent;
import com.servicio.reservas.reportes.application.services.ReportReservationService;
import com.servicio.reservas.reportes.application.dto.CreateReservationEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ReservaEventListener {

    private final ObjectMapper objectMapper;
    private final ReportReservationService reservaReporteService;

    public ReservaEventListener(ObjectMapper objectMapper,
                                ReportReservationService reservaReporteService) {
        this.objectMapper = objectMapper;
        this.reservaReporteService = reservaReporteService;
    }

    @RabbitListener(queues = "${reportes.reservas.cola}")
    public void recibirEvento(String mensaje, @Header("amqp_receivedRoutingKey") String routingKey) {
        try {
            switch (routingKey) {
                case "reserva.creada":
                    CreateReservationEvent eventCreado =
                            objectMapper.readValue(mensaje, CreateReservationEvent.class);
                    reservaReporteService.procesarReservaCreada(eventCreado);
                    break;

                case "reserva.cancelada":
                    CancelReservationEvent eventCancelada =
                            objectMapper.readValue(mensaje, CancelReservationEvent.class);
                    reservaReporteService.procesarReservaCancelada(eventCancelada);
                    break;

                case "reserva.completada":
                    CompletedReservationEvent eventCompletada =
                            objectMapper.readValue(mensaje, CompletedReservationEvent.class);
                    reservaReporteService.procesarReservaCompletada(eventCompletada);
                    break;

                default:
                    System.out.println("Evento desconocido: " + routingKey);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


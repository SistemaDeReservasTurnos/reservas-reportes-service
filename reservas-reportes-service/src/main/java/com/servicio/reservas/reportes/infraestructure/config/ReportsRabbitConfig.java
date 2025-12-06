package com.servicio.reservas.reportes.infraestructure.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;


@Configuration
public class ReportsRabbitConfig {

    @Value("${agenda.events.exchange}")
    private String agendaEventsExchange;

    @Value("${reportes.reservas.cola}")
    private String reportesReservasCola;

    // 1. Declaración de la cola
    @Bean
    public Queue reservasReportesQueue() {
        return new Queue(reportesReservasCola, true);
    }

    // 2. Bind de la cola al exchange, escuchando eventos de reservas
    @Bean
    public Binding reservaCreadaBinding(Queue reservasReportesQueue, TopicExchange agendaEventsExchange) {
        return BindingBuilder
                .bind(reservasReportesQueue)
                .to(agendaEventsExchange)
                .with("reserva.*"); // escucha todos los eventos: reserva.creada, reserva.cancelada, etc.
    }

    @Bean
    public TopicExchange agendaEventsExchange() {
        return new TopicExchange(agendaEventsExchange, true, false);
    }
}

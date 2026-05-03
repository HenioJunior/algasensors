package com.algasensors.temperature.monitoring.infra.messaging.consumer;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogResponse;
import com.algasensors.temperature.monitoring.application.usecase.temperature.ProcessTemperatureReadingUseCase;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import com.algasensors.temperature.monitoring.infra.messaging.dto.TemperatureProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
@Slf4j
public class TemperatureProcessedConsumer {

    private final ProcessTemperatureReadingUseCase processTemperatureReadingUseCase;

    @KafkaListener(
            topics = "${app.kafka.topics.processed-reading}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(TemperatureProcessedEvent event) {
        System.out.println("[DEBUG_LOG] Received processed event: " + event);
        log.debug("Received processed temperature reading: {}", event);

        try {
            // Mapeia o evento para o DTO que o Use Case espera
            // O Use Case atualmente espera TemperatureLogResponse, que é um DTO de saída da API
            // Em uma refatoração futura, seria ideal o Use Case usar um modelo interno ou o próprio evento
            TemperatureLogResponse logResponse = TemperatureLogResponse.builder()
                    .id(event.eventId())
                    .sensorId(SensorId.of(event.sensorId()))
                    .registeredAt(event.occurredAt().atOffset(ZoneOffset.UTC))
                    .value(new BigDecimal(event.temperature()))
                    .build();

            processTemperatureReadingUseCase.execute(logResponse);
        } catch (NumberFormatException e) {
            log.error("Failed to parse temperature value: '{}' for sensor: '{}', eventId: '{}'",
                    event.temperature(), event.sensorId(), event.eventId());
        } catch (Exception e) {
            System.err.println("[DEBUG_LOG] Error in TemperatureProcessedConsumer: " + e.getMessage());
            e.printStackTrace();
            log.error("Unexpected error processing temperature event: '{}', eventId: '{}'",
                    event, event.eventId(), e);
        }
    }
}

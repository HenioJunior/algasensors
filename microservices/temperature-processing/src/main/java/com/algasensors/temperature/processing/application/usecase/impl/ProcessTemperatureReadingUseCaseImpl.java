package com.algasensors.temperature.processing.application.usecase.impl;

import com.algasensors.temperature.processing.application.usecase.ProcessTemperatureReadingUseCase;
import com.algasensors.temperature.processing.domain.model.TemperatureReading;
import com.algasensors.temperature.processing.domain.model.TemperatureTechnicalLog;
import com.algasensors.temperature.processing.domain.valueobject.SensorId;
import com.algasensors.temperature.processing.gateways.TemperatureProcessedEventPublisher;
import com.algasensors.temperature.processing.gateways.TemperatureTechnicalLogGateway;
import com.algasensors.temperature.processing.infra.messaging.dto.TemperatureMessage;
import com.algasensors.temperature.processing.infra.messaging.event.TemperatureProcessedEvent;
import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ProcessTemperatureReadingUseCaseImpl implements ProcessTemperatureReadingUseCase {

    private final TemperatureTechnicalLogGateway technicalLogGateway;
    private final TemperatureProcessedEventPublisher processedEventPublisher;

    public ProcessTemperatureReadingUseCaseImpl(TemperatureTechnicalLogGateway technicalLogGateway, TemperatureProcessedEventPublisher processedEventPublisher) {
        this.technicalLogGateway = technicalLogGateway;
        this.processedEventPublisher = processedEventPublisher;
    }

    @Override
    public void execute(TemperatureMessage message) {
        TemperatureTechnicalLog technicalLog = null;

        try {
            TemperatureReading reading = TemperatureReading.of(
                    SensorId.of(message.sensorId()),
                    message.temperature(),
                    message.unit(),
                    message.occurredAt()
            );

            technicalLog = technicalLogGateway.saveReceived(
                    message.messageId(),
                    reading
            );

            Instant processedAt = Instant.now();
            String eventId = TSID.fast().toString();

            TemperatureProcessedEvent event = new TemperatureProcessedEvent(
                    eventId,
                    reading.getSensorId().getValue(),
                    reading.getTemperature().toPlainString(),
                    reading.getUnit(),
                    reading.getOccurredAt(),
                    processedAt,
                    new TemperatureProcessedEvent.QualityPayload(true, false),
                    new TemperatureProcessedEvent.SourcePayload(
                            "temperature-processing",
                            "temperature.raw.v1"
                    )
            );

            processedEventPublisher.publish(event);

            technicalLogGateway.markAsProcessed(
                    technicalLog.getId(),
                    eventId,
                    processedAt
            );

        } catch (Exception ex) {
            if (technicalLog != null) {
                technicalLogGateway.markAsFailed(
                        technicalLog.getId(),
                        ex.getMessage()
                );
            }

            throw ex;
        }
    }
}

package com.algasensors.temperature.processing.application.usecase.impl;

import com.algasensors.temperature.processing.application.usecase.ProcessTemperatureReadingUseCase;
import com.algasensors.temperature.processing.domain.model.TemperatureReading;
import com.algasensors.temperature.processing.domain.valueobject.SensorId;
import com.algasensors.temperature.processing.gateways.TemperatureProcessedEventPublisher;
import com.algasensors.temperature.processing.gateways.TemperatureTechnicalLogGateway;
import com.algasensors.temperature.processing.infra.messaging.dto.TemperatureMessage;
import com.algasensors.temperature.processing.infra.messaging.event.TemperatureProcessedEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class ProcessTemperatureReadingUseCaseImpl implements ProcessTemperatureReadingUseCase {

    private final TemperatureProcessedEventPublisher publisher;
    private final TemperatureTechnicalLogGateway technicalLogGateway;

    public ProcessTemperatureReadingUseCaseImpl(
            TemperatureProcessedEventPublisher publisher,
            TemperatureTechnicalLogGateway technicalLogGateway
    ) {
        this.publisher = publisher;
        this.technicalLogGateway = technicalLogGateway;
    }

    @Override
    public void execute(TemperatureMessage message) {
        SensorId sensorId = SensorId.of(message.sensorId());
        BigDecimal temperature = BigDecimal.valueOf(message.temperature());

        TemperatureReading reading = TemperatureReading.of(
                sensorId,
                temperature,
                message.unit(),
                message.occurredAt()
        );

        technicalLogGateway.saveReceived(reading);

        TemperatureProcessedEvent event = new TemperatureProcessedEvent(
                UUID.randomUUID().toString(),
                reading.getSensorId().getValue(),
                reading.getTemperature().toPlainString(),
                reading.getUnit(),
                reading.getOccurredAt(),
                Instant.now(),
                new TemperatureProcessedEvent.QualityPayload(true, true),
                new TemperatureProcessedEvent.SourcePayload(
                        "temperature-processing",
                        "temperature.raw.v1"
                )
        );

        publisher.publish(event);

        technicalLogGateway.saveProcessed(reading);
    }
}

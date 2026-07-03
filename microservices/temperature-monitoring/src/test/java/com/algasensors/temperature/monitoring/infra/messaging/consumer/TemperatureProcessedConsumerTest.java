package com.algasensors.temperature.monitoring.infra.messaging.consumer;

import com.algasensors.temperature.monitoring.application.usecase.temperature.ProcessTemperatureReadingUseCase;
import com.algasensors.temperature.monitoring.infra.messaging.dto.TemperatureProcessedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TemperatureProcessedConsumerTest {

    private ProcessTemperatureReadingUseCase processTemperatureReadingUseCase;
    private TemperatureProcessedConsumer consumer;

    @BeforeEach
    void setUp() {
        processTemperatureReadingUseCase = Mockito.mock(ProcessTemperatureReadingUseCase.class);
        consumer = new TemperatureProcessedConsumer(processTemperatureReadingUseCase);
    }

    @Test
    void listen_WhenTemperatureIsInvalid_ShouldNotThrowExceptionAndNotCallUseCase() {
        TemperatureProcessedEvent event = new TemperatureProcessedEvent(
                "event-123",
                "0BH5H2Y7K0000",
                "invalid-temp",
                "CELSIUS",
                Instant.now(),
                Instant.now(),
                new TemperatureProcessedEvent.QualityPayload(true, false),
                new TemperatureProcessedEvent.SourcePayload("test", "test")
        );

        consumer.listen(event);

        Mockito.verifyNoInteractions(processTemperatureReadingUseCase);
    }
}

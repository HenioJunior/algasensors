package com.algasensors.temperature.processing.application.usecase.impl;

import com.algasensors.temperature.processing.domain.model.TemperatureReading;
import com.algasensors.temperature.processing.domain.model.TemperatureTechnicalLog;
import com.algasensors.temperature.processing.gateways.TemperatureProcessedEventPublisher;
import com.algasensors.temperature.processing.gateways.TemperatureTechnicalLogGateway;
import com.algasensors.temperature.processing.infra.messaging.dto.TemperatureMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProcessTemperatureReadingUseCaseImplTest {

    private TemperatureTechnicalLogGateway technicalLogGateway;
    private TemperatureProcessedEventPublisher processedEventPublisher;
    private ProcessTemperatureReadingUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        technicalLogGateway = mock(TemperatureTechnicalLogGateway.class);
        processedEventPublisher = mock(TemperatureProcessedEventPublisher.class);
        useCase = new ProcessTemperatureReadingUseCaseImpl(technicalLogGateway, processedEventPublisher);
    }

    @Test
    void execute_WhenSaveReceivedReturnsNull_ShouldThrowExceptionAndNotMarkAsFailed() {
        // This simulates the problematic state before the fix
        TemperatureMessage message = new TemperatureMessage(
                "msg-123", "sensor-456", new BigDecimal("25.5"), "CELSIUS", Instant.now()
        );
        
        when(technicalLogGateway.saveReceived(anyString(), any(TemperatureReading.class))).thenReturn(null);

        assertThrows(NullPointerException.class, () -> useCase.execute(message));
        
        verify(technicalLogGateway, never()).markAsFailed(anyString(), anyString());
    }

    @Test
    void execute_WhenEverythingIsOk_ShouldProcessAndMarkAsProcessed() {
        TemperatureMessage message = new TemperatureMessage(
                "msg-123", "sensor-456", new BigDecimal("25.5"), "CELSIUS", Instant.now()
        );
        
        TemperatureTechnicalLog technicalLog = mock(TemperatureTechnicalLog.class);
        when(technicalLog.getId()).thenReturn("log-789");
        when(technicalLogGateway.saveReceived(anyString(), any(TemperatureReading.class))).thenReturn(technicalLog);

        useCase.execute(message);

        verify(technicalLogGateway).saveReceived(eq("msg-123"), any(TemperatureReading.class));
        verify(processedEventPublisher).publish(any());
        verify(technicalLogGateway).markAsProcessed(eq("log-789"), anyString(), any(Instant.class));
    }
}

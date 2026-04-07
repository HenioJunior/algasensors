package com.algasensors.temperature.monitoring.application.usecase.impl;

import com.algasensors.temperature.monitoring.application.gateway.SensorMonitoringGateway;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.impl.ValidateSensorMonitoringExistsUseCaseImpl;
import com.algasensors.temperature.monitoring.domain.exception.SensorMonitoringNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidateSensorMonitoringExistsUseCaseImplTest {

    @Mock
    private SensorMonitoringGateway sensorMonitoringGateway;

    @InjectMocks
    private ValidateSensorMonitoringExistsUseCaseImpl useCase;

    private SensorId sensorId;

    @BeforeEach
    void setUp() {
        sensorId = SensorId.generate();
    }

    @Test
    void shouldValidateSuccessfullyWhenSensorMonitoringExists() {
        when(sensorMonitoringGateway.findById(sensorId)).thenReturn(Optional.of(mock(SensorMonitoring.class)));

        assertDoesNotThrow(() -> useCase.execute(sensorId));

        verify(sensorMonitoringGateway).findById(sensorId);
        verifyNoMoreInteractions(sensorMonitoringGateway);
    }

    @Test
    void shouldThrowExceptionWhenSensorMonitoringDoesNotExist() {
        when(sensorMonitoringGateway.findById(sensorId)).thenReturn(Optional.empty());

        assertThrows(SensorMonitoringNotFoundException.class, () -> useCase.execute(sensorId));

        verify(sensorMonitoringGateway).findById(sensorId);
        verifyNoMoreInteractions(sensorMonitoringGateway);
    }
}

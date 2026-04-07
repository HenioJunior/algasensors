package com.algasensors.temperature.monitoring.application.usecase.impl;

import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.alert.impl.DeleteSensorAlertUseCaseImpl;
import com.algasensors.temperature.monitoring.domain.exception.SensorAlertNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteSensorAlertUseCaseImplTest {

    @Mock
    private SensorAlertGateway sensorAlertGateway;

    @InjectMocks
    private DeleteSensorAlertUseCaseImpl useCase;

    private SensorId sensorId;
    private SensorAlert sensorAlert;

    @BeforeEach
    void setUp() {
        sensorId = SensorId.generate();
        sensorAlert = mock(SensorAlert.class);
    }

    @Test
    void shouldDeleteSensorAlertSuccessfully() {
        when(sensorAlertGateway.findById(sensorId)).thenReturn(Optional.of(sensorAlert));

        useCase.execute(sensorId);

        verify(sensorAlertGateway).findById(sensorId);
        verify(sensorAlertGateway).delete(sensorAlert);
        verifyNoMoreInteractions(sensorAlertGateway);
    }

    @Test
    void shouldThrowExceptionWhenSensorAlertDoesNotExist() {
        when(sensorAlertGateway.findById(sensorId)).thenReturn(Optional.empty());

        assertThrows(SensorAlertNotFoundException.class, () -> useCase.execute(sensorId));

        verify(sensorAlertGateway).findById(sensorId);
        verify(sensorAlertGateway, never()).delete(any());
        verifyNoMoreInteractions(sensorAlertGateway);
    }
}
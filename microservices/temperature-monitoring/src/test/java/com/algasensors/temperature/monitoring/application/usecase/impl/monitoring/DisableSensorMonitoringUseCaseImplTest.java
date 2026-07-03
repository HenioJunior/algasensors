package com.algasensors.temperature.monitoring.application.usecase.impl.monitoring;

import com.algasensors.temperature.monitoring.application.gateway.SensorMonitoringGateway;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.impl.DisableSensorMonitoringUseCaseImpl;
import com.algasensors.temperature.monitoring.domain.exception.SensorMonitoringNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisableSensorMonitoringUseCaseImplTest {

    @Mock
    private SensorMonitoringGateway sensorMonitoringGateway;

    @InjectMocks
    private DisableSensorMonitoringUseCaseImpl useCase;

    @Test
    void shouldDisableAndSaveSuccessfully() {
        SensorId sensorId = mock(SensorId.class);
        SensorMonitoring sensorMonitoring = mock(SensorMonitoring.class);

        when(sensorMonitoringGateway.findById(sensorId)).thenReturn(Optional.of(sensorMonitoring));

        try (MockedStatic<SensorMonitoring> mockedStatic = mockStatic(SensorMonitoring.class)) {
            useCase.execute(sensorId);

            verify(sensorMonitoringGateway).findById(sensorId);
            mockedStatic.verify(() -> SensorMonitoring.disable(sensorMonitoring));
            verify(sensorMonitoringGateway).save(sensorMonitoring);
            verifyNoMoreInteractions(sensorMonitoringGateway);
        }
    }

    @Test
    void shouldThrowExceptionWhenSensorMonitoringNotFound() {
        SensorId sensorId = mock(SensorId.class);
        when(sensorMonitoringGateway.findById(sensorId)).thenReturn(Optional.empty());

        assertThrows(SensorMonitoringNotFoundException.class, () -> useCase.execute(sensorId));

        verify(sensorMonitoringGateway).findById(sensorId);
        verify(sensorMonitoringGateway, never()).save(any());
        verifyNoMoreInteractions(sensorMonitoringGateway);
    }
}

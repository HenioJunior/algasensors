package com.algasensors.temperature.monitoring.application.usecase.impl.monitoring;

import com.algasensors.temperature.monitoring.application.gateway.SensorMonitoringGateway;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.impl.CreateSensorMonitoringUseCaseImpl;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSensorMonitoringUseCaseImplTest {

    @Mock
    private SensorMonitoringGateway sensorMonitoringGateway;

    @InjectMocks
    private CreateSensorMonitoringUseCaseImpl useCase;

    @Test
    void shouldCreateAndSaveSensorMonitoringSuccessfully() {
        SensorId sensorId = mock(SensorId.class);
        SensorMonitoring sensorMonitoring = mock(SensorMonitoring.class);

        try (MockedStatic<SensorMonitoring> mockedStatic = mockStatic(SensorMonitoring.class)) {
            mockedStatic.when(() -> SensorMonitoring.create(sensorId)).thenReturn(sensorMonitoring);

            SensorMonitoring result = useCase.execute(sensorId);

            assertSame(sensorMonitoring, result);
            mockedStatic.verify(() -> SensorMonitoring.create(sensorId));
            verify(sensorMonitoringGateway).save(sensorMonitoring);
            verifyNoMoreInteractions(sensorMonitoringGateway);
        }
    }
}

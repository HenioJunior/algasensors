package com.algasensors.temperature.monitoring.application.usecase.impl.alert;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.alert.impl.UpdateSensorAlertUseCaseImpl;
import com.algasensors.temperature.monitoring.domain.exception.SensorAlertNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSensorAlertUseCaseImplTest {

    @Mock
    private SensorAlertGateway sensorAlertGateway;

    @InjectMocks
    private UpdateSensorAlertUseCaseImpl useCase;

    private SensorId sensorId;
    private SensorAlertRequest request;
    private SensorAlert sensorAlert;

    @BeforeEach
    void setUp() {
        sensorId = SensorId.generate();

        request = new SensorAlertRequest();
        request.setMinTemperature(BigDecimal.valueOf(10));
        request.setMaxTemperature(BigDecimal.valueOf(50));

        sensorAlert = mock(SensorAlert.class);
    }

    @Test
    void shouldUpdateSensorAlertSuccessfully() {
        when(sensorAlertGateway.findById(sensorId)).thenReturn(Optional.of(sensorAlert));

        useCase.execute(sensorId, request);

        verify(sensorAlertGateway).findById(sensorId);
        verify(sensorAlert).updateTemperatureRange(
                request.getMinTemperature(),
                request.getMaxTemperature()
        );
        verify(sensorAlertGateway).save(sensorAlert);
        verifyNoMoreInteractions(sensorAlertGateway);
    }

    @Test
    void shouldThrowExceptionWhenSensorAlertDoesNotExist() {
        when(sensorAlertGateway.findById(sensorId)).thenReturn(Optional.empty());

        assertThrows(SensorAlertNotFoundException.class, () -> useCase.execute(sensorId, request));

        verify(sensorAlertGateway).findById(sensorId);
        verify(sensorAlertGateway, never()).save(any());
    }
}

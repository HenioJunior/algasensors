package com.algasensors.temperature.monitoring.application.usecase.temperature.impl;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogResponse;
import com.algasensors.temperature.monitoring.application.gateway.SensorMonitoringGateway;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.CreateMonitoringUseCase;
import com.algasensors.temperature.monitoring.application.usecase.temperature.CreateTemperatureLogUseCase;
import com.algasensors.temperature.monitoring.application.usecase.temperature.UpdateSensorMonitoringFromReadingUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.service.ProcessTemperatureAlertUseCase;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessTemperatureReadingUseCaseImplTest {

    @Mock
    private SensorMonitoringGateway sensorMonitoringGateway;
    @Mock
    private CreateMonitoringUseCase createMonitoringUseCase;
    @Mock
    private UpdateSensorMonitoringFromReadingUseCase updateSensorMonitoringFromReadingUseCase;
    @Mock
    private CreateTemperatureLogUseCase createTemperatureLogUseCase;
    @Mock
    private ProcessTemperatureAlertUseCase processTemperatureAlertUseCase;

    @InjectMocks
    private ProcessTemperatureReadingUseCaseImpl useCase;

    private SensorId sensorId;
    private TemperatureLogResponse reading;

    @BeforeEach
    void setUp() {
        sensorId = SensorId.generate();
        reading = TemperatureLogResponse.builder()
                .sensorId(sensorId)
                .value(new BigDecimal("25.5"))
                .build();
    }

    @Test
    void shouldCreateNewMonitoringWhenSensorNotFound() {
        when(sensorMonitoringGateway.findById(sensorId)).thenReturn(Optional.empty());
        SensorMonitoring newMonitoring = SensorMonitoring.create(sensorId);
        when(createMonitoringUseCase.execute(sensorId)).thenReturn(newMonitoring);

        useCase.execute(reading);

        verify(sensorMonitoringGateway).findById(sensorId);
        verify(createMonitoringUseCase).execute(sensorId);
        verify(updateSensorMonitoringFromReadingUseCase).execute(newMonitoring, reading);
        verify(createTemperatureLogUseCase).execute(reading);
        verify(processTemperatureAlertUseCase).execute(reading);
    }

    @Test
    void shouldUpdateExistingMonitoringWhenSensorFound() {
        SensorMonitoring existingMonitoring = SensorMonitoring.create(sensorId);
        when(sensorMonitoringGateway.findById(sensorId)).thenReturn(Optional.of(existingMonitoring));

        useCase.execute(reading);

        verify(sensorMonitoringGateway).findById(sensorId);
        verify(createMonitoringUseCase, never()).execute(any());
        verify(updateSensorMonitoringFromReadingUseCase).execute(existingMonitoring, reading);
    }

    @Test
    void shouldIgnoreReadingWhenSensorIsDisabled() {
        SensorMonitoring disabledMonitoring = SensorMonitoring.create(sensorId);
        disabledMonitoring.setEnabled(false);
        when(sensorMonitoringGateway.findById(sensorId)).thenReturn(Optional.of(disabledMonitoring));

        useCase.execute(reading);

        verify(sensorMonitoringGateway).findById(sensorId);
        verify(updateSensorMonitoringFromReadingUseCase, never()).execute(any(), any());
        verify(createTemperatureLogUseCase, never()).execute(any());
        verify(processTemperatureAlertUseCase, never()).execute(any());
    }
}

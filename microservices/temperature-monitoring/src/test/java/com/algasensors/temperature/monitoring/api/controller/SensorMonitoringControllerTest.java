package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorMonitoringResponseMapper;
import com.algasensors.temperature.monitoring.api.response.SensorMonitoringResponse;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.CreateMonitoringUseCase;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.DisableSensorMonitoringUseCase;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.EnableSensorMonitoringUseCase;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.ValidateSensorMonitoringExistsUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorMonitoringControllerTest {

    @Mock
    private ValidateSensorMonitoringExistsUseCase validateSensorMonitoringExistsUseCase;

    @Mock
    private CreateMonitoringUseCase createMonitoringUseCase;

    @Mock
    private EnableSensorMonitoringUseCase enableSensorMonitoringUseCase;

    @Mock
    private DisableSensorMonitoringUseCase disableSensorMonitoringUseCase;

    @Mock
    private SensorMonitoringResponseMapper sensorMonitoringResponseMapper;

    @InjectMocks
    private SensorMonitoringController controller;

    private SensorId sensorId;
    private SensorMonitoring sensorMonitoring;
    private SensorMonitoringResponse response;

    @BeforeEach
    void setUp() {
        sensorId = mock(SensorId.class);
        sensorMonitoring = mock(SensorMonitoring.class);
        response = mock(SensorMonitoringResponse.class);
    }

    @Test
    void shouldGetDetailSuccessfully() {
        when(validateSensorMonitoringExistsUseCase.execute(sensorId)).thenReturn(sensorMonitoring);
        when(sensorMonitoringResponseMapper.toResponse(sensorMonitoring)).thenReturn(response);

        SensorMonitoringResponse result = controller.getDetail(sensorId);

        assertSame(response, result);
        verify(validateSensorMonitoringExistsUseCase).execute(sensorId);
        verify(sensorMonitoringResponseMapper).toResponse(sensorMonitoring);
        verifyNoMoreInteractions(
                validateSensorMonitoringExistsUseCase,
                createMonitoringUseCase,
                enableSensorMonitoringUseCase,
                disableSensorMonitoringUseCase,
                sensorMonitoringResponseMapper
        );
    }

    @Test
    void shouldCreateSuccessfully() {
        when(createMonitoringUseCase.execute(sensorId)).thenReturn(sensorMonitoring);
        when(sensorMonitoringResponseMapper.toResponse(sensorMonitoring)).thenReturn(response);

        SensorMonitoringResponse result = controller.create(sensorId);

        assertSame(response, result);
        verify(createMonitoringUseCase).execute(sensorId);
        verify(sensorMonitoringResponseMapper).toResponse(sensorMonitoring);
        verifyNoMoreInteractions(
                validateSensorMonitoringExistsUseCase,
                createMonitoringUseCase,
                enableSensorMonitoringUseCase,
                disableSensorMonitoringUseCase,
                sensorMonitoringResponseMapper
        );
    }

    @Test
    void shouldEnableSuccessfully() {
        doNothing().when(enableSensorMonitoringUseCase).execute(sensorId);

        controller.enable(sensorId);

        verify(enableSensorMonitoringUseCase).execute(sensorId);
        verifyNoMoreInteractions(
                validateSensorMonitoringExistsUseCase,
                createMonitoringUseCase,
                enableSensorMonitoringUseCase,
                disableSensorMonitoringUseCase,
                sensorMonitoringResponseMapper
        );
    }

    @Test
    void shouldDisableSuccessfully() {
        doNothing().when(disableSensorMonitoringUseCase).execute(sensorId);

        controller.disable(sensorId);

        verify(disableSensorMonitoringUseCase).execute(sensorId);
        verifyNoMoreInteractions(
                validateSensorMonitoringExistsUseCase,
                createMonitoringUseCase,
                enableSensorMonitoringUseCase,
                disableSensorMonitoringUseCase,
                sensorMonitoringResponseMapper
        );
    }
}
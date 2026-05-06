package com.algasensors.temperature.monitoring.application.usecase.impl.alert;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.alert.impl.CreateOrUpdateOrUpdateSensorAlertUseCase;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.FindSensorMonitoringByIdUseCase;
import com.algasensors.temperature.monitoring.domain.exception.SensorMonitoringNotFoundException;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrUpdateSensorAlertUseCaseTest {

    @Mock
    private SensorAlertGateway sensorAlertGateway;

    @Mock
    private FindSensorMonitoringByIdUseCase findSensorMonitoringByIdUseCase;

    @InjectMocks
    private CreateOrUpdateOrUpdateSensorAlertUseCase useCase;

    private SensorId sensorId;
    private SensorAlertRequest request;

    @BeforeEach
    void setUp() {
        sensorId = SensorId.generate();

        request = new SensorAlertRequest();
        request.setMinTemperature(BigDecimal.valueOf(10));
        request.setMaxTemperature(BigDecimal.valueOf(50));
    }

    @Test
    void shouldValidateSensorMonitoringAndCreateSensorAlertSuccessfully() {
        ArgumentCaptor<SensorAlert> sensorAlertCaptor = ArgumentCaptor.forClass(SensorAlert.class);

        when(sensorAlertGateway.save(sensorAlertCaptor.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SensorAlert result = useCase.execute(sensorId, request);

        verify(findSensorMonitoringByIdUseCase).execute(sensorId);
        verify(sensorAlertGateway).save(sensorAlertCaptor.getValue());

        SensorAlert savedAlert = sensorAlertCaptor.getValue();

        assertNotNull(result);
        assertNotNull(savedAlert);
        assertSame(savedAlert, result);
    }

    @Test
    void shouldThrowExceptionWhenSensorMonitoringDoesNotExist() {
        doThrow(new SensorMonitoringNotFoundException(sensorId))
                .when(findSensorMonitoringByIdUseCase)
                .execute(sensorId);

        assertThrows(SensorMonitoringNotFoundException.class, () ->
                useCase.execute(sensorId, request)
        );

        verify(findSensorMonitoringByIdUseCase).execute(sensorId);
        verifyNoInteractions(sensorAlertGateway);
    }
}
package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.CreateSensorUseCase;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.infra.client.SensorMonitoringClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSensorUseCaseImplTest {

    @Mock
    private SensorGateway sensorGateway;

    @Mock
    private SensorMonitoringClient sensorMonitoringClient;

    @InjectMocks
    private CreateSensorUseCaseImpl createSensorUseCase;

    @Test
    void shouldCreateSensorSuccessfully() {
        CreateSensorUseCase.CreateSensorCommand command =
                new CreateSensorUseCase.CreateSensorCommand(
                        "Sensor A",
                        "Sala 01",
                        "192.168.0.10",
                        "MQTT",
                        "Model X"
                );

        Sensor result = createSensorUseCase.execute(command);

        assertNotNull(result);

        ArgumentCaptor<Sensor> sensorCaptor = ArgumentCaptor.forClass(Sensor.class);

        verify(sensorGateway).save(sensorCaptor.capture());

        Sensor savedSensor = sensorCaptor.getValue();
        assertNotNull(savedSensor);
        assertNotNull(savedSensor.getId());
        assertEquals("Sensor A", savedSensor.getName());
        assertEquals("Sala 01", savedSensor.getLocation());
        assertEquals("192.168.0.10", savedSensor.getIp());
        assertEquals("MQTT", savedSensor.getProtocol());
        assertEquals("Model X", savedSensor.getModel());

        verify(sensorMonitoringClient).create(savedSensor.getId());

        assertSame(savedSensor, result);

        verifyNoMoreInteractions(sensorGateway, sensorMonitoringClient);
    }
}

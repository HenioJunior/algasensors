package com.algasensors.device.management.application.service.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.CreateSensorUseCase;
import com.algasensors.device.management.application.usecase.impl.CreateSensorUseCaseImpl;
import com.algasensors.device.management.domain.model.Sensor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateSensorUseCaseImplTest {

    @Mock
    private SensorGateway sensorGateway;

    @InjectMocks
    private CreateSensorUseCaseImpl createSensorUseCase;

    @Test
    void shouldCreateSensorWithDisabledByDefault() {
        var command = new CreateSensorUseCase.CreateSensorCommand(
                "Sensor 01",
                "Câmara Fria",
                "192.168.0.10",
                "MQTT",
                "DHT22"
        );

        when(sensorGateway.save(any(Sensor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Sensor sensor = createSensorUseCase.execute(command);

        ArgumentCaptor<Sensor> captor = ArgumentCaptor.forClass(Sensor.class);
        verify(sensorGateway).save(captor.capture());

        Sensor savedSensor = captor.getValue();

        assertThat(savedSensor.getId()).isNotNull();
        assertThat(savedSensor.getName()).isEqualTo("Sensor 01");
        assertThat(savedSensor.getLocation()).isEqualTo("Câmara Fria");
        assertThat(savedSensor.getIp()).isEqualTo("192.168.0.10");
        assertThat(savedSensor.getProtocol()).isEqualTo("MQTT");
        assertThat(savedSensor.getModel()).isEqualTo("DHT22");
        assertThat(savedSensor.getEnabled()).isFalse();

        assertThat(sensor).isSameAs(savedSensor);
    }

}

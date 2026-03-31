package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.UpdateSensorUseCase;
import com.algasensors.device.management.application.usecase.support.SensorIdParser;
import com.algasensors.device.management.domain.exception.InvalidSensorIdException;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSensorUseCaseImplTest {

    @Mock
    private SensorGateway sensorGateway;

    @Mock
    private SensorIdParser sensorIdParser;

    @InjectMocks
    private UpdateSensorUseCaseImpl updateSensorUseCase;

    @Test
    void shouldUpdateSensorSuccessfully() {
        TSID tsid = TSID.fast();
        SensorId sensorId = new SensorId(tsid);

        Sensor sensor = Sensor.builder()
                .id(sensorId)
                .name("Sensor antigo")
                .location("Local antigo")
                .ip("192.168.0.10")
                .protocol("HTTP")
                .model("Modelo antigo")
                .enabled(true)
                .build();

        UpdateSensorUseCase.Command command = new UpdateSensorUseCase.Command(
                tsid.toString(),
                "Sensor novo",
                "Novo local",
                "10.0.0.20",
                "MQTT",
                "DHT22"
        );

        when(sensorIdParser.parse(command.sensorId())).thenReturn(sensorId);
        when(sensorGateway.findById(sensorId)).thenReturn(Optional.of(sensor));
        when(sensorGateway.save(sensor)).thenReturn(sensor);

        Sensor result = updateSensorUseCase.execute(command);

        assertThat(result).isSameAs(sensor);
        assertThat(result.getName()).isEqualTo("Sensor novo");
        assertThat(result.getLocation()).isEqualTo("Novo local");
        assertThat(result.getIp()).isEqualTo("10.0.0.20");
        assertThat(result.getProtocol()).isEqualTo("MQTT");
        assertThat(result.getModel()).isEqualTo("DHT22");
        assertThat(result.getEnabled()).isTrue();

        verify(sensorIdParser).parse(command.sensorId());
        verify(sensorGateway).findById(sensorId);
        verify(sensorGateway).save(sensor);
        verifyNoMoreInteractions(sensorGateway);
    }

    @Test
    void shouldThrowWhenSensorIdIsInvalid() {
        String rawSensorId = "id-invalido";

        UpdateSensorUseCase.Command command = new UpdateSensorUseCase.Command(
                rawSensorId,
                "Sensor novo",
                "Novo local",
                "10.0.0.20",
                "MQTT",
                "DHT22"
        );

        when(sensorIdParser.parse(rawSensorId))
                .thenThrow(new InvalidSensorIdException(rawSensorId));

        assertThatThrownBy(() -> updateSensorUseCase.execute(command))
                .isInstanceOf(InvalidSensorIdException.class)
                .hasMessageContaining(rawSensorId);

        verify(sensorIdParser).parse(rawSensorId);
        verifyNoInteractions(sensorGateway);
    }

    @Test
    void shouldThrowWhenSensorDoesNotExist() {
        TSID tsid = TSID.fast();
        SensorId sensorId = new SensorId(tsid);

        UpdateSensorUseCase.Command command = new UpdateSensorUseCase.Command(
                tsid.toString(),
                "Sensor novo",
                "Novo local",
                "10.0.0.20",
                "MQTT",
                "DHT22"
        );

        when(sensorIdParser.parse(command.sensorId())).thenReturn(sensorId);
        when(sensorGateway.findById(sensorId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateSensorUseCase.execute(command))
                .isInstanceOf(SensorNotFoundException.class)
                .hasMessageContaining(tsid.toString());

        verify(sensorIdParser).parse(command.sensorId());
        verify(sensorGateway).findById(sensorId);
        verify(sensorGateway, never()).save(any());
    }
}

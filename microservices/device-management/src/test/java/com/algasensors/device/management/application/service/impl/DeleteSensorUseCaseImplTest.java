package com.algasensors.device.management.application.service.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.DeleteSensorUseCase;
import com.algasensors.device.management.application.usecase.impl.DeleteSensorUseCaseImpl;
import com.algasensors.device.management.application.support.SensorIdParser;
import com.algasensors.device.management.domain.exception.InvalidSensorIdException;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.valueobject.SensorId;
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
class DeleteSensorUseCaseImplTest {

    @Mock
    private SensorGateway sensorGateway;

    @Mock
    private SensorIdParser sensorIdParser;

    @InjectMocks
    private DeleteSensorUseCaseImpl deleteSensorUseCase;

    @Test
    void shouldDeleteSensorSuccessfully() {
        TSID tsid = TSID.fast();
        SensorId sensorId = new SensorId(tsid);

        Sensor sensor = Sensor.builder()
                .id(sensorId)
                .name("Sensor 01")
                .location("Lab")
                .ip("192.168.0.10")
                .protocol("MQTT")
                .model("DHT22")
                .enabled(true)
                .build();

        DeleteSensorUseCase.Command command = new DeleteSensorUseCase.Command(tsid.toString());

        when(sensorIdParser.parse(command.sensorId())).thenReturn(sensorId);
        when(sensorGateway.findById(sensorId)).thenReturn(Optional.of(sensor));

        deleteSensorUseCase.execute(command);

        verify(sensorIdParser).parse(command.sensorId());
        verify(sensorGateway).findById(sensorId);
        verify(sensorGateway).delete(sensor);
        verifyNoMoreInteractions(sensorGateway);
    }

    @Test
    void shouldThrowWhenSensorIdIsInvalid() {
        String rawSensorId = "id-invalido";
        DeleteSensorUseCase.Command command = new DeleteSensorUseCase.Command(rawSensorId);

        when(sensorIdParser.parse(rawSensorId))
                .thenThrow(new InvalidSensorIdException(rawSensorId));

        assertThatThrownBy(() -> deleteSensorUseCase.execute(command))
                .isInstanceOf(InvalidSensorIdException.class)
                .hasMessageContaining(rawSensorId);

        verify(sensorIdParser).parse(rawSensorId);
        verifyNoInteractions(sensorGateway);
    }

    @Test
    void shouldThrowWhenSensorDoesNotExist() {
        TSID tsid = TSID.fast();
        SensorId sensorId = new SensorId(tsid);
        DeleteSensorUseCase.Command command = new DeleteSensorUseCase.Command(tsid.toString());

        when(sensorIdParser.parse(command.sensorId())).thenReturn(sensorId);
        when(sensorGateway.findById(sensorId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deleteSensorUseCase.execute(command))
                .isInstanceOf(SensorNotFoundException.class)
                .hasMessageContaining(tsid.toString());

        verify(sensorIdParser).parse(command.sensorId());
        verify(sensorGateway).findById(sensorId);
        verify(sensorGateway, never()).delete(any());
    }
}
package com.algasensors.device.management.application.service.impl;

import com.algasensors.device.management.application.gateway.SensorMonitoringGateway;
import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.EnableSensorUseCase;
import com.algasensors.device.management.application.usecase.impl.EnableSensorUseCaseImpl;
import com.algasensors.device.management.application.support.SensorIdParser;
import com.algasensors.device.management.domain.exception.InvalidSensorIdException;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnableSensorUseCaseImplTest {

    @Mock
    private SensorGateway sensorGateway;

    @Mock
    private SensorMonitoringGateway sensorMonitoringGateway;

    @Mock
    private SensorIdParser sensorIdParser;

    @InjectMocks
    private EnableSensorUseCaseImpl enableSensorUseCase;

    @Test
    void shouldEnableSensorAndMonitoringSuccessfully() {
        TSID tsid = TSID.fast();
        SensorId sensorId = new SensorId(tsid);

        Sensor sensor = Sensor.builder()
                .id(sensorId)
                .name("Sensor 01")
                .location("Lab")
                .ip("192.168.0.10")
                .protocol("MQTT")
                .model("DHT22")
                .enabled(false)
                .build();

        EnableSensorUseCase.Command command = new EnableSensorUseCase.Command(tsid.toString());

        when(sensorIdParser.parse(command.sensorId())).thenReturn(sensorId);
        when(sensorGateway.findById(sensorId)).thenReturn(Optional.of(sensor));
        when(sensorGateway.save(sensor)).thenReturn(sensor);

        enableSensorUseCase.execute(command);

        assertThat(sensor.getEnabled()).isTrue();

        InOrder inOrder = inOrder(sensorIdParser, sensorGateway, sensorMonitoringGateway);
        inOrder.verify(sensorIdParser).parse(command.sensorId());
        inOrder.verify(sensorGateway).findById(sensorId);
        inOrder.verify(sensorGateway).save(sensor);
        inOrder.verify(sensorMonitoringGateway).enableMonitoring(tsid);

        verifyNoMoreInteractions(sensorGateway, sensorMonitoringGateway);
    }

    @Test
    void shouldThrowWhenSensorIdIsInvalid() {
        String rawSensorId = "id-invalido";
        EnableSensorUseCase.Command command = new EnableSensorUseCase.Command(rawSensorId);

        when(sensorIdParser.parse(rawSensorId))
                .thenThrow(new InvalidSensorIdException(rawSensorId));

        assertThatThrownBy(() -> enableSensorUseCase.execute(command))
                .isInstanceOf(InvalidSensorIdException.class)
                .hasMessageContaining(rawSensorId);

        verify(sensorIdParser).parse(rawSensorId);
        verifyNoInteractions(sensorGateway, sensorMonitoringGateway);
    }

    @Test
    void shouldThrowWhenSensorDoesNotExist() {
        TSID tsid = TSID.fast();
        SensorId sensorId = new SensorId(tsid);
        EnableSensorUseCase.Command command = new EnableSensorUseCase.Command(tsid.toString());

        when(sensorIdParser.parse(command.sensorId())).thenReturn(sensorId);
        when(sensorGateway.findById(sensorId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enableSensorUseCase.execute(command))
                .isInstanceOf(SensorNotFoundException.class)
                .hasMessageContaining(tsid.toString());

        verify(sensorIdParser).parse(command.sensorId());
        verify(sensorGateway).findById(sensorId);
        verify(sensorGateway, never()).save(any());
        verifyNoInteractions(sensorMonitoringGateway);
    }

    @Test
    void shouldNotCallMonitoringClientWhenSaveFails() {
        TSID tsid = TSID.fast();
        SensorId sensorId = new SensorId(tsid);

        Sensor sensor = Sensor.builder()
                .id(sensorId)
                .name("Sensor 01")
                .location("Lab")
                .ip("192.168.0.10")
                .protocol("MQTT")
                .model("DHT22")
                .enabled(false)
                .build();

        EnableSensorUseCase.Command command = new EnableSensorUseCase.Command(tsid.toString());

        when(sensorIdParser.parse(command.sensorId())).thenReturn(sensorId);
        when(sensorGateway.findById(sensorId)).thenReturn(Optional.of(sensor));
        when(sensorGateway.save(sensor)).thenThrow(new RuntimeException("erro ao salvar"));

        assertThatThrownBy(() -> enableSensorUseCase.execute(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("erro ao salvar");

        assertThat(sensor.getEnabled()).isTrue();

        verify(sensorGateway).save(sensor);
        verifyNoInteractions(sensorMonitoringGateway);
    }
}
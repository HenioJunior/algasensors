package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.support.SensorIdParser;
import com.algasensors.device.management.application.usecase.DisableSensorUseCase;
import com.algasensors.device.management.domain.exception.InvalidSensorIdException;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.valueobject.SensorId;
import com.algasensors.device.management.infra.client.SensorMonitoringClient;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisableSensorUseCaseImplTest {

    @Mock
    private SensorGateway sensorGateway;

    @Mock
    private SensorMonitoringClient sensorMonitoringClient;

    @Mock
    private SensorIdParser sensorIdParser;

    @InjectMocks
    private DisableSensorUseCaseImpl disableSensorUseCase;

    @Test
    void shouldDisableSensorAndMonitoringSuccessfully() {
        SensorId sensorId = SensorId.generate();

        Sensor sensor = Sensor.builder()
                .id(sensorId)
                .name("Sensor 01")
                .location("Lab")
                .ip("192.168.0.10")
                .protocol("MQTT")
                .model("DHT22")
                .enabled(true)
                .build();

        DisableSensorUseCase.Command command = new DisableSensorUseCase.Command(sensorId.toString());

        when(sensorIdParser.parse(command.sensorId())).thenReturn(sensorId);
        when(sensorGateway.findById(sensorId)).thenReturn(Optional.of(sensor));
        when(sensorGateway.save(sensor)).thenReturn(sensor);

        disableSensorUseCase.execute(command);

        assertThat(sensor.getEnabled()).isFalse();

        InOrder inOrder = inOrder(sensorIdParser, sensorGateway, sensorMonitoringClient);
        inOrder.verify(sensorIdParser).parse(command.sensorId());
        inOrder.verify(sensorGateway).findById(sensorId);
        inOrder.verify(sensorGateway).save(sensor);
        inOrder.verify(sensorMonitoringClient).disableMonitoring(sensorId);

        verifyNoMoreInteractions(sensorGateway, sensorMonitoringClient);
    }

    @Test
    void shouldThrowWhenSensorIdIsInvalid() {
        String rawSensorId = "id-invalido";
        DisableSensorUseCase.Command command = new DisableSensorUseCase.Command(rawSensorId);

        when(sensorIdParser.parse(rawSensorId))
                .thenThrow(new InvalidSensorIdException(rawSensorId));

        assertThatThrownBy(() -> disableSensorUseCase.execute(command))
                .isInstanceOf(InvalidSensorIdException.class)
                .hasMessageContaining(rawSensorId);

        verify(sensorIdParser).parse(rawSensorId);
        verifyNoInteractions(sensorGateway, sensorMonitoringClient);
    }

    @Test
    void shouldThrowWhenSensorDoesNotExist() {
        TSID tsid = TSID.fast();
        SensorId sensorId = new SensorId(tsid);
        DisableSensorUseCase.Command command = new DisableSensorUseCase.Command(tsid.toString());

        when(sensorIdParser.parse(command.sensorId())).thenReturn(sensorId);
        when(sensorGateway.findById(sensorId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> disableSensorUseCase.execute(command))
                .isInstanceOf(SensorNotFoundException.class)
                .hasMessageContaining(tsid.toString());

        verify(sensorIdParser).parse(command.sensorId());
        verify(sensorGateway).findById(sensorId);
        verify(sensorGateway, never()).save(any());
        verifyNoInteractions(sensorMonitoringClient);
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
                .enabled(true)
                .build();

        DisableSensorUseCase.Command command = new DisableSensorUseCase.Command(tsid.toString());

        when(sensorIdParser.parse(command.sensorId())).thenReturn(sensorId);
        when(sensorGateway.findById(sensorId)).thenReturn(Optional.of(sensor));
        when(sensorGateway.save(sensor)).thenThrow(new RuntimeException("erro ao salvar"));

        assertThatThrownBy(() -> disableSensorUseCase.execute(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("erro ao salvar");

        assertThat(sensor.getEnabled()).isFalse();

        verify(sensorGateway).save(sensor);
        verifyNoInteractions(sensorMonitoringClient);
    }
}
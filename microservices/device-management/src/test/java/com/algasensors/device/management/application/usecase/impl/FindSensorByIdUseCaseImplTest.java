package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.FindSensorByIdUseCase;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindSensorByIdUseCaseImplTest {

    @Mock
    private SensorGateway sensorGateway;

    @InjectMocks
    private FindSensorByIdUseCaseImpl findSensorByIdUseCase;

    @Test
    void shouldReturnSensorWhenIdExists() {
        TSID tsid = TSID.fast();
        Sensor sensor = Sensor.builder()
                .id(new SensorId(tsid))
                .name("Sensor 01")
                .location("Lab")
                .ip("192.168.0.20")
                .protocol("MQTT")
                .model("DHT22")
                .enabled(true)
                .build();

        when(sensorGateway.findById(new SensorId(tsid)))
                .thenReturn(Optional.of(sensor));

        Sensor result = findSensorByIdUseCase.execute(
                new FindSensorByIdUseCase.Query(tsid.toString())
        );

        assertThat(result).isSameAs(sensor);
    }

    @Test
    void shouldThrowWhenSensorDoesNotExist() {
        TSID tsid = TSID.fast();

        when(sensorGateway.findById(new SensorId(tsid)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                findSensorByIdUseCase.execute(new FindSensorByIdUseCase.Query(tsid.toString()))
        ).isInstanceOf(SensorNotFoundException.class)
                .hasMessageContaining(tsid.toString());
    }

    @Test
    void shouldThrowWhenSensorIdIsInvalid() {
        assertThatThrownBy(() ->
                findSensorByIdUseCase.execute(new FindSensorByIdUseCase.Query("id-invalido"))
        ).isInstanceOf(InvalidSensorIdException.class);
    }
}

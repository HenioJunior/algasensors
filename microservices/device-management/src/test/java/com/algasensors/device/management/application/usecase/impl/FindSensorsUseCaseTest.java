package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.impl.FindSensorsUseCaseImpl;
import com.algasensors.device.management.domain.model.Sensor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindSensorsUseCaseTest {

    @Mock
    private SensorGateway sensorGateway;

    @InjectMocks
    private FindSensorsUseCaseImpl findSensorsUseCase;

    @Test
    void shouldReturnPagedSensors() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name"));

        Page<Sensor> page = new PageImpl<>(List.of(
                Sensor.builder().name("Sensor A").build(),
                Sensor.builder().name("Sensor B").build()
        ), pageable, 2);

        when(sensorGateway.findAll(pageable)).thenReturn(page);

        Page<Sensor> result = findSensorsUseCase.execute(pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
    }
}

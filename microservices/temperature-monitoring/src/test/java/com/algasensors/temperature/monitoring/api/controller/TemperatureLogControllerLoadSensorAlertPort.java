package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import com.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TemperatureLogControllerLoadSensorAlertPort {

    private TemperatureLogRepository temperatureLogRepository;
    private TemperatureLogController controller;

    @BeforeEach
    void setUp() {
        temperatureLogRepository = mock(TemperatureLogRepository.class);
        controller = new TemperatureLogController(temperatureLogRepository);
    }

    @Test
    @DisplayName("Deve buscar logs de temperatura por sensor e mapear para TemperatureLogData")
    void shouldSearchTemperatureLogsBySensorId() {
        TSID sensorId = TSID.fast();
        TSID logId = TSID.fast();
        OffsetDateTime registeredAt = OffsetDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);

        TemperatureLog temperatureLog = TemperatureLog.builder()
                .id(new TemperatureLogId(logId))
                .sensorId(new SensorId(sensorId))
                .temperatureValue(BigDecimal.valueOf(26.7))
                .registeredAt(registeredAt)
                .build();

        Page<TemperatureLog> page = new PageImpl<>(List.of(temperatureLog), pageable, 1);

        when(temperatureLogRepository.findAllBySensorId(new SensorId(sensorId), pageable))
                .thenReturn(page);

        Page<TemperatureLogData> result = controller.search(sensorId, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).hasSize(1);

        TemperatureLogData data = result.getContent().get(0);
        assertThat(data.getId()).isEqualTo(logId.toString());
        assertThat(data.getSensorId()).isEqualTo(new SensorId(sensorId));
        assertThat(data.getValue()).isEqualByComparingTo("26.7");
        assertThat(data.getRegisteredAt()).isEqualTo(registeredAt);

        verify(temperatureLogRepository).findAllBySensorId(new SensorId(sensorId), pageable);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando não houver logs para o sensor")
    void shouldReturnEmptyPageWhenNoTemperatureLogsExist() {
        TSID sensorId = TSID.fast();
        Pageable pageable = PageRequest.of(0, 10);

        when(temperatureLogRepository.findAllBySensorId(new SensorId(sensorId), pageable))
                .thenReturn(Page.empty(pageable));

        Page<TemperatureLogData> result = controller.search(sensorId, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isZero();

        verify(temperatureLogRepository).findAllBySensorId(new SensorId(sensorId), pageable);
    }
}

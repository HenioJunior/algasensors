package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.model.SensorMonitoringOutput;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SensorMonitoringControllerLoadSensorAlertPort {

    private SensorMonitoringRepository sensorMonitoringRepository;
    private SensorMonitoringController controller;

    @BeforeEach
    void setUp() {
        sensorMonitoringRepository = mock(SensorMonitoringRepository.class);
        controller = new SensorMonitoringController(sensorMonitoringRepository);
    }

    @Test
    @DisplayName("Deve retornar detalhe do monitoramento quando existir")
    void shouldReturnMonitoringDetailWhenExists() {
        TSID sensorId = TSID.fast();
        OffsetDateTime updatedAt = OffsetDateTime.now();

        SensorMonitoring sensorMonitoring = SensorMonitoring.builder()
                .id(new SensorId(sensorId))
                .enabled(true)
                .lastTemperature(new BigDecimal(27.5))
                .updatedAt(updatedAt)
                .build();

        when(sensorMonitoringRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.of(sensorMonitoring));

        SensorMonitoringOutput output = controller.getDetail(sensorId);

        assertThat(output).isNotNull();
        assertThat(output.getId()).isEqualTo(sensorId);
        assertThat(output.isEnabled()).isTrue();
        assertEquals(0, output.getLastTemperature().compareTo(BigDecimal.valueOf(27.5)));
        assertThat(output.getUpdatedAt()).isEqualTo(updatedAt);

        verify(sensorMonitoringRepository).findById(new SensorId(sensorId));
    }

    @Test
    @DisplayName("Deve retornar monitoramento padrão quando não existir")
    void shouldReturnDefaultMonitoringWhenNotExists() {
        TSID sensorId = TSID.fast();

        when(sensorMonitoringRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.empty());

        SensorMonitoringOutput output = controller.getDetail(sensorId);

        assertThat(output).isNotNull();
        assertThat(output.getId()).isEqualTo(sensorId);
        assertThat(output.isEnabled()).isFalse();
        assertThat(output.getLastTemperature()).isNull();
        assertThat(output.getUpdatedAt()).isNull();

        verify(sensorMonitoringRepository).findById(new SensorId(sensorId));
    }

    @Test
    @DisplayName("Deve habilitar monitoramento quando estiver desabilitado")
    void shouldEnableMonitoringWhenDisabled() {
        TSID sensorId = TSID.fast();

        SensorMonitoring sensorMonitoring = SensorMonitoring.builder()
                .id(new SensorId(sensorId))
                .enabled(false)
                .lastTemperature(null)
                .updatedAt(null)
                .build();

        when(sensorMonitoringRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.of(sensorMonitoring));

        controller.enable(sensorId);

        assertThat(sensorMonitoring.isEnabled()).isTrue();
        verify(sensorMonitoringRepository).findById(new SensorId(sensorId));
        verify(sensorMonitoringRepository).saveAndFlush(sensorMonitoring);
    }

    @Test
    @DisplayName("Deve criar monitoramento padrão e habilitar quando não existir")
    void shouldCreateDefaultAndEnableMonitoringWhenNotExists() {
        TSID sensorId = TSID.fast();

        when(sensorMonitoringRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.empty());

        controller.enable(sensorId);

        verify(sensorMonitoringRepository).findById(new SensorId(sensorId));

        verify(sensorMonitoringRepository).saveAndFlush(argThat(sensorMonitoring ->
                sensorMonitoring.getId().equals(new SensorId(sensorId)) &&
                        sensorMonitoring.isEnabled() &&
                        sensorMonitoring.getLastTemperature() == null &&
                        sensorMonitoring.getUpdatedAt() == null
        ));
    }

    @Test
    @DisplayName("Deve retornar 422 ao tentar habilitar monitoramento já habilitado")
    void shouldThrow422WhenEnableAlreadyEnabledMonitoring() {
        TSID sensorId = TSID.fast();

        SensorMonitoring sensorMonitoring = SensorMonitoring.builder()
                .id(new SensorId(sensorId))
                .enabled(true)
                .build();

        when(sensorMonitoringRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.of(sensorMonitoring));

        assertThatThrownBy(() -> controller.enable(sensorId))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(422);

        verify(sensorMonitoringRepository).findById(new SensorId(sensorId));
        verify(sensorMonitoringRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("Deve desabilitar monitoramento quando estiver habilitado")
    void shouldDisableMonitoringWhenEnabled() {
        TSID sensorId = TSID.fast();

        SensorMonitoring sensorMonitoring = SensorMonitoring.builder()
                .id(new SensorId(sensorId))
                .enabled(true)
                .lastTemperature(new BigDecimal(25.0))
                .updatedAt(OffsetDateTime.now())
                .build();

        when(sensorMonitoringRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.of(sensorMonitoring));

        controller.disable(sensorId);

        assertThat(sensorMonitoring.isEnabled()).isFalse();
        verify(sensorMonitoringRepository).findById(new SensorId(sensorId));
        verify(sensorMonitoringRepository).saveAndFlush(sensorMonitoring);
    }

    @Test
    @DisplayName("Deve retornar 422 ao tentar desabilitar monitoramento já desabilitado")
    void shouldThrow422WhenDisableAlreadyDisabledMonitoring() {
        TSID sensorId = TSID.fast();

        SensorMonitoring sensorMonitoring = SensorMonitoring.builder()
                .id(new SensorId(sensorId))
                .enabled(false)
                .build();

        when(sensorMonitoringRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.of(sensorMonitoring));

        assertThatThrownBy(() -> controller.disable(sensorId))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(422);

        verify(sensorMonitoringRepository, never()).saveAndFlush(any());
    }
}

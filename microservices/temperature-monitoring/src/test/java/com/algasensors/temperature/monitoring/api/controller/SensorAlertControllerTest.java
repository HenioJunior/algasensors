package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.model.SensorAlertInput;
import com.algasensors.temperature.monitoring.api.model.SensorAlertOutput;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SensorAlertControllerTest {

    private SensorAlertRepository sensorAlertRepository;
    private SensorAlertController controller;

    @BeforeEach
    void setUp() {
        sensorAlertRepository = mock(SensorAlertRepository.class);
        controller = new SensorAlertController(sensorAlertRepository);
    }

    @Test
    @DisplayName("Deve buscar alerta por sensorId com sucesso")
    void shouldGetAlertSuccessfully() {
        TSID sensorId = TSID.fast();

        SensorAlert sensorAlert = SensorAlert.builder()
                .id(new SensorId(sensorId))
                .minTemperature(10.0)
                .maxTemperature(35.0)
                .build();

        when(sensorAlertRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.of(sensorAlert));

        ResponseEntity<SensorAlertOutput> response = controller.getAlert(sensorId);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(sensorId);
        assertThat(response.getBody().getMinTemperature()).isEqualTo(10.0);
        assertThat(response.getBody().getMaxTemperature()).isEqualTo(35.0);

        verify(sensorAlertRepository).findById(new SensorId(sensorId));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar alerta inexistente")
    void shouldThrowNotFoundWhenGetAlertAndAlertDoesNotExist() {
        TSID sensorId = TSID.fast();

        when(sensorAlertRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.getAlert(sensorId))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(404);

        verify(sensorAlertRepository).findById(new SensorId(sensorId));
    }

    @Test
    @DisplayName("Deve criar alerta com sucesso")
    void shouldCreateAlertSuccessfully() {
        SensorAlertInput input = new SensorAlertInput();
        input.setMinTemperature(12.5);
        input.setMaxTemperature(40.0);

        when(sensorAlertRepository.save(any(SensorAlert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SensorAlertOutput output = controller.createAlert(input);

        assertThat(output).isNotNull();
        assertThat(output.getId()).isNotNull();
        assertThat(output.getMinTemperature()).isEqualTo(12.5);
        assertThat(output.getMaxTemperature()).isEqualTo(40.0);

        verify(sensorAlertRepository).save(any(SensorAlert.class));
    }

    @Test
    @DisplayName("Deve atualizar alerta com sucesso")
    void shouldUpdateAlertSuccessfully() {
        TSID sensorId = TSID.fast();

        SensorAlert existingAlert = SensorAlert.builder()
                .id(new SensorId(sensorId))
                .minTemperature(10.0)
                .maxTemperature(30.0)
                .build();

        SensorAlertInput input = new SensorAlertInput();
        input.setMinTemperature(15.0);
        input.setMaxTemperature(45.0);

        when(sensorAlertRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.of(existingAlert));

        controller.updateAlert(sensorId, input);

        assertThat(existingAlert.getMinTemperature()).isEqualTo(15.0);
        assertThat(existingAlert.getMaxTemperature()).isEqualTo(45.0);

        verify(sensorAlertRepository).findById(new SensorId(sensorId));
        verify(sensorAlertRepository).save(existingAlert);
    }

    @Test
    @DisplayName("Deve retornar 404 ao atualizar alerta inexistente")
    void shouldThrowNotFoundWhenUpdateAlertAndAlertDoesNotExist() {
        TSID sensorId = TSID.fast();

        SensorAlertInput input = new SensorAlertInput();
        input.setMinTemperature(15.0);
        input.setMaxTemperature(45.0);

        when(sensorAlertRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.updateAlert(sensorId, input))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(404);

        verify(sensorAlertRepository).findById(new SensorId(sensorId));
        verify(sensorAlertRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve excluir alerta com sucesso")
    void shouldDeleteAlertSuccessfully() {
        TSID sensorId = TSID.fast();

        SensorAlert existingAlert = SensorAlert.builder()
                .id(new SensorId(sensorId))
                .minTemperature(8.0)
                .maxTemperature(28.0)
                .build();

        when(sensorAlertRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.of(existingAlert));

        controller.deleteAlert(sensorId);

        verify(sensorAlertRepository).findById(new SensorId(sensorId));
        verify(sensorAlertRepository).delete(existingAlert);
    }

    @Test
    @DisplayName("Deve retornar 404 ao excluir alerta inexistente")
    void shouldThrowNotFoundWhenDeleteAlertAndAlertDoesNotExist() {
        TSID sensorId = TSID.fast();

        when(sensorAlertRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.deleteAlert(sensorId))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(404);

        verify(sensorAlertRepository).findById(new SensorId(sensorId));
        verify(sensorAlertRepository, never()).delete(any());
    }
}
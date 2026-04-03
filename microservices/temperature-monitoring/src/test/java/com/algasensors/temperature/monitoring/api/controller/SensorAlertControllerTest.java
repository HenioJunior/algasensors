package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorAlertResponseMapper;
import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.api.response.SensorAlertResponse;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SensorAlertControllerTest {

    private SensorAlertRepository sensorAlertRepository;
    private SensorAlertResponseMapper sensorAlertResponseMapper;
    private SensorAlertResponse responseMock;
    private SensorAlertController controller;

    @BeforeEach
    void setUp() {
        sensorAlertRepository = mock(SensorAlertRepository.class);
        sensorAlertResponseMapper = mock(SensorAlertResponseMapper.class);
        controller = new SensorAlertController(sensorAlertRepository, sensorAlertResponseMapper);
    }

    @Test
    @DisplayName("Deve buscar alerta por sensorId com sucesso")
    void shouldGetAlertSuccessfully() {
        TSID sensorId = TSID.fast();

        SensorAlert sensorAlert = SensorAlert.builder()
                .id(new SensorId(sensorId))
                .minTemperature(new BigDecimal("10.0"))
                .maxTemperature(new BigDecimal("35.0"))
                .build();

        when(sensorAlertRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.of(sensorAlert));
        when(sensorAlertResponseMapper.toResponse(any())).thenReturn(responseMock);

        ResponseEntity<SensorAlertResponse> response = controller.getAlert(sensorId);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(sensorId);
        assertEquals(0, response.getBody().getMinTemperature().compareTo(BigDecimal.valueOf(10.0)));
        assertEquals(0, response.getBody().getMaxTemperature().compareTo(BigDecimal.valueOf(35.0)));

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
        SensorAlertRequest request = new SensorAlertRequest();
        request.setMinTemperature(new BigDecimal(12.5));
        request.setMaxTemperature(new BigDecimal(40.0));

        when(sensorAlertRepository.save(any(SensorAlert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SensorAlertResponse response = controller.createAlert(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertEquals(0, request.getMinTemperature().compareTo(BigDecimal.valueOf(12.5)));
        assertEquals(0, request.getMaxTemperature().compareTo(BigDecimal.valueOf(40.0)));

        verify(sensorAlertRepository).save(any(SensorAlert.class));
    }

    @Test
    @DisplayName("Deve atualizar alerta com sucesso")
    void shouldUpdateAlertSuccessfully() {
        TSID sensorId = TSID.fast();

        SensorAlert existingAlert = SensorAlert.builder()
                .id(new SensorId(sensorId))
                .minTemperature(new BigDecimal(10.0))
                .maxTemperature(new BigDecimal(30.0))
                .build();

        SensorAlertRequest request = new SensorAlertRequest();
        request.setMinTemperature(new BigDecimal(15.0));
        request.setMaxTemperature(new BigDecimal(45.0));

        when(sensorAlertRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.of(existingAlert));

        controller.updateAlert(sensorId, request);

        assertEquals(0, existingAlert.getMinTemperature().compareTo(BigDecimal.valueOf(15.0)));
        assertEquals(0, existingAlert.getMaxTemperature().compareTo(BigDecimal.valueOf(45.0)));

        verify(sensorAlertRepository).findById(new SensorId(sensorId));
        verify(sensorAlertRepository).save(existingAlert);
    }

    @Test
    @DisplayName("Deve retornar 404 ao atualizar alerta inexistente")
    void shouldThrowNotFoundWhenUpdateAlertAndAlertDoesNotExist() {
        TSID sensorId = TSID.fast();

        SensorAlertRequest request = new SensorAlertRequest();
        request.setMinTemperature(new BigDecimal(15.0));
        request.setMaxTemperature(new BigDecimal(45.0));

        when(sensorAlertRepository.findById(new SensorId(sensorId)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.updateAlert(sensorId, request))
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
                .minTemperature(new BigDecimal(8.0))
                .maxTemperature(new BigDecimal(28.0))
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
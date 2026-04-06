package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorAlertResponseMapper;
import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.api.response.SensorAlertResponse;
import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SensorAlertControllerTest {

    private SensorAlertGateway sensorAlertGateway;
    private SensorAlertResponseMapper sensorAlertResponseMapper;
    private SensorAlertResponse responseMock;
    private SensorAlertController controller;

    @BeforeEach
   void setUp() {
        sensorAlertGateway = mock(SensorAlertGateway.class);
        sensorAlertResponseMapper = mock(SensorAlertResponseMapper.class);
        controller = new SensorAlertController(sensorAlertGateway, sensorAlertResponseMapper);

        responseMock = SensorAlertResponse.builder()
                .id(SensorId.of(1L))
                .maxTemperature(BigDecimal.valueOf(40))
                .minTemperature(BigDecimal.valueOf(10))
                .build();
    }

    @Test
    @DisplayName("Deve retornar SensorAlertResponse quando o alerta existir")
    void shouldReturnSensorAlertResponseWhenAlertExists() {
        // Arrange

        SensorAlert sensorAlert = mock(SensorAlert.class);

        when(sensorAlertGateway.findById(SensorId.of(1L)))
                .thenReturn(Optional.of(sensorAlert));

        when(sensorAlertResponseMapper.toResponse(sensorAlert))
                .thenReturn(responseMock);

        // Act
        ResponseEntity<SensorAlertResponse> response = controller.getAlertById(SensorId.of(1L));

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseMock, response.getBody());
    }



    @Test
    @DisplayName("Deve retornar 404 ao buscar alerta inexistente")
    void shouldThrowNotFoundWhenGetAlertAndAlertByIdDoesNotExist() {
        SensorId sensorId = SensorId.of(1L);

        when(sensorAlertGateway.findById(sensorId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.getAlertById(sensorId))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(404);

        verify(sensorAlertGateway).findById(sensorId);
    }

    @Test
    @DisplayName("Deve criar alerta com sucesso")
    void shouldCreateAlertSuccessfully() {
        SensorAlertRequest request = new SensorAlertRequest();
        request.setMinTemperature(new BigDecimal("12.5"));
        request.setMaxTemperature(new BigDecimal("40.0"));

        when(sensorAlertResponseMapper.toResponse(any(SensorAlert.class)))
                .thenReturn(responseMock);

        when(sensorAlertGateway.save(any(SensorAlert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SensorAlertResponse response = controller.createAlert(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertEquals(0, request.getMinTemperature().compareTo(BigDecimal.valueOf(12.5)));
        assertEquals(0, request.getMaxTemperature().compareTo(BigDecimal.valueOf(40.0)));

        verify(sensorAlertGateway).save(any(SensorAlert.class));
    }

    @Test
    @DisplayName("Deve atualizar alerta com sucesso")
    void shouldUpdateAlertSuccessfully() {
        SensorId sensorId = SensorId.of(1L);

        SensorAlert existingAlert = SensorAlert.builder()
                .id(sensorId)
                .minTemperature(new BigDecimal("10.0"))
                .maxTemperature(new BigDecimal("30.0"))
                .build();

        SensorAlertRequest request = new SensorAlertRequest();
        request.setMinTemperature(new BigDecimal("15.0"));
        request.setMaxTemperature(new BigDecimal("45.0"));

        when(sensorAlertGateway.findById(sensorId))
                .thenReturn(Optional.of(existingAlert));

        controller.updateAlert(sensorId, request);

        assertEquals(0, existingAlert.getMinTemperature().compareTo(BigDecimal.valueOf(15.0)));
        assertEquals(0, existingAlert.getMaxTemperature().compareTo(BigDecimal.valueOf(45.0)));

        verify(sensorAlertGateway).findById(sensorId);
        verify(sensorAlertGateway).save(existingAlert);
    }

    @Test
    @DisplayName("Deve retornar 404 ao atualizar alerta inexistente")
    void shouldThrowNotFoundWhenUpdateAlertAndAlertDoesNotExist() {
        SensorId sensorId = SensorId.of(1L);

        SensorAlertRequest request = new SensorAlertRequest();
        request.setMinTemperature(new BigDecimal(15.0));
        request.setMaxTemperature(new BigDecimal(45.0));

        when(sensorAlertGateway.findById(sensorId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.updateAlert(sensorId, request))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(404);

        verify(sensorAlertGateway).findById(sensorId);
        verify(sensorAlertGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve excluir alerta com sucesso")
    void shouldDeleteAlertSuccessfully() {
        SensorId sensorId = SensorId.of(1L);

        SensorAlert existingAlert = SensorAlert.builder()
                .id(sensorId)
                .minTemperature(new BigDecimal("8.0"))
                .maxTemperature(new BigDecimal("28.0"))
                .build();

        when(sensorAlertGateway.findById(sensorId))
                .thenReturn(Optional.of(existingAlert));

        controller.deleteAlert(sensorId);

        verify(sensorAlertGateway).findById(sensorId);
        verify(sensorAlertGateway).delete(existingAlert);
    }

    @Test
    @DisplayName("Deve retornar 404 ao excluir alerta inexistente")
    void shouldThrowNotFoundWhenDeleteAlertAndAlertDoesNotExist() {
        SensorId sensorId = SensorId.of(1L);

        when(sensorAlertGateway.findById(sensorId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.deleteAlert(sensorId))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(404);

        verify(sensorAlertGateway).findById(sensorId);
        verify(sensorAlertGateway, never()).delete(any());
    }
}
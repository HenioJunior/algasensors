package com.algasensors.device.management;

import com.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algasensors.device.management.api.controller.SensorController;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import com.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SensorController.class)
class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorRepository sensorRepository;

    @MockBean
    private SensorMonitoringClient sensorMonitoringClient;

    @Test
    @DisplayName("Deve buscar sensores com sucesso")
    void shouldSearchSensors() throws Exception {
        TSID tsid = TSID.fast();
        Sensor sensor = Sensor.builder()
                .id(new SensorId(tsid))
                .name("Sensor A")
                .ip("192.168.0.10")
                .location("Sala 1")
                .protocol("MQTT")
                .model("ESP32")
                .enabled(true)
                .build();

        when(sensorRepository.findAll(any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sensor), PageRequest.of(0, 20), 1));

        mockMvc.perform(get("/api/sensors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(tsid.toString()))
                .andExpect(jsonPath("$.content[0].name").value("Sensor A"))
                .andExpect(jsonPath("$.content[0].enabled").value(true));
    }

    @Test
    @DisplayName("Deve buscar um sensor por id com sucesso")
    void shouldGetOneSensor() throws Exception {
        TSID tsid = TSID.fast();
        Sensor sensor = Sensor.builder()
                .id(new SensorId(tsid))
                .name("Sensor B")
                .ip("192.168.0.11")
                .location("Sala 2")
                .protocol("HTTP")
                .model("Model X")
                .enabled(false)
                .build();

        when(sensorRepository.findById(any(SensorId.class))).thenReturn(Optional.of(sensor));

        mockMvc.perform(get("/api/sensors/{sensorId}", tsid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tsid.toString()))
                .andExpect(jsonPath("$.name").value("Sensor B"))
                .andExpect(jsonPath("$.enabled").value(false));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar sensor inexistente")
    void shouldReturn404WhenSensorNotFound() throws Exception {
        TSID tsid = TSID.fast();

        when(sensorRepository.findById(any(SensorId.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sensors/{sensorId}", tsid))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve criar sensor com sucesso")
    void shouldCreateSensor() throws Exception {
        TSID tsid = TSID.fast();

        Sensor savedSensor = Sensor.builder()
                .id(new SensorId(tsid))
                .name("Novo Sensor")
                .ip("10.0.0.1")
                .location("Lab")
                .protocol("MQTT")
                .model("ESP8266")
                .enabled(false)
                .build();

        when(sensorRepository.saveAndFlush(any(Sensor.class))).thenReturn(savedSensor);

        String body = """
            {
              "name": "Novo Sensor",
              "ip": "10.0.0.1",
              "location": "Lab",
              "protocol": "MQTT",
              "model": "ESP8266"
            }
            """;

        mockMvc.perform(post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(tsid.toString()))
                .andExpect(jsonPath("$.name").value("Novo Sensor"))
                .andExpect(jsonPath("$.enabled").value(false));

        ArgumentCaptor<Sensor> captor = ArgumentCaptor.forClass(Sensor.class);
        verify(sensorRepository).saveAndFlush(captor.capture());
        assertThat(captor.getValue().getEnabled()).isFalse();
    }

    @Test
    @DisplayName("Deve ativar sensor e chamar monitoramento")
    void shouldEnableSensor() throws Exception {
        TSID tsid = TSID.fast();

        Sensor sensor = Sensor.builder()
                .id(new SensorId(tsid))
                .name("Sensor C")
                .ip("10.0.0.2")
                .location("Sala 3")
                .protocol("MQTT")
                .model("ESP32")
                .enabled(false)
                .build();

        when(sensorRepository.findById(any(SensorId.class))).thenReturn(Optional.of(sensor));
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensor);

        mockMvc.perform(put("/api/sensors/{sensorId}/enable", tsid))
                .andExpect(status().isNoContent());

        assertThat(sensor.getEnabled()).isTrue();
        verify(sensorRepository).save(sensor);
        verify(sensorMonitoringClient).enableMonitoring(tsid);
    }

    @Test
    @DisplayName("Deve desativar sensor e chamar monitoramento")
    void shouldDisableSensor() throws Exception {
        TSID tsid = TSID.fast();

        Sensor sensor = Sensor.builder()
                .id(new SensorId(tsid))
                .name("Sensor D")
                .ip("10.0.0.3")
                .location("Sala 4")
                .protocol("HTTP")
                .model("Model Y")
                .enabled(true)
                .build();

        when(sensorRepository.findById(any(SensorId.class))).thenReturn(Optional.of(sensor));
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensor);

        mockMvc.perform(delete("/api/sensors/{sensorId}/enable", tsid))
                .andExpect(status().isNoContent());

        assertThat(sensor.getEnabled()).isFalse();
        verify(sensorRepository).save(sensor);
        verify(sensorMonitoringClient).disableMonitoring(tsid);
    }

    @Test
    @DisplayName("Deve excluir sensor com sucesso")
    void shouldDeleteSensor() throws Exception {
        TSID tsid = TSID.fast();

        Sensor sensor = Sensor.builder()
                .id(new SensorId(tsid))
                .name("Sensor E")
                .ip("10.0.0.4")
                .location("Sala 5")
                .protocol("MQTT")
                .model("Model Z")
                .enabled(true)
                .build();

        when(sensorRepository.findById(any(SensorId.class))).thenReturn(Optional.of(sensor));

        mockMvc.perform(delete("/api/sensors/{sensorId}", tsid))
                .andExpect(status().isNoContent());

        verify(sensorRepository).delete(sensor);
    }
}

package com.algasensors.temperature.processing.api.controller;

import com.algasensors.temperature.processing.api.model.SensorId;
import com.algasensors.temperature.processing.api.model.TemperatureLogResponse;
import com.algasensors.temperature.processing.infra.kafka.sensor.TemperatureMessage;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures/data")
@Slf4j
@RequiredArgsConstructor
public class TemperatureProcessingController {

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public void data(@PathVariable("sensorId") SensorId sensorId, @RequestBody String input) {
        if (input == null || input.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }

        double temperature;

        try {
            temperature = Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }

        TemperatureLogResponse logoutput = TemperatureLogResponse
                .builder()
                .sensorId(sensorId)
                .value(temperature)
                .registeredAt(OffsetDateTime.now())
                .build();

        log.info(logoutput.toString());

    }

    @KafkaListener(topics = "temperature_topic", groupId = "temperature-processing-group")
    public void consumeTemperatureMessage(TemperatureMessage temperatureMessage) {
        // Processo para salvar ou registrar a temperatura recebida
        if (temperatureMessage == null) {
            throw new IllegalArgumentException("Received null temperature message");
        }

        double temperature = temperatureMessage.getTemperature();
        SensorId sensorId = temperatureMessage.getSensorId();

        // Criar o log de temperatura
        TemperatureLogResponse logoutput = TemperatureLogResponse
                .builder()
                .sensorId(sensorId)
                .value(temperature)
                .registeredAt(OffsetDateTime.now())
                .build();

        // Aqui você pode salvar o log ou realizar algum outro processamento necessário
        System.out.println("Received temperature: " + logoutput);
    }
}



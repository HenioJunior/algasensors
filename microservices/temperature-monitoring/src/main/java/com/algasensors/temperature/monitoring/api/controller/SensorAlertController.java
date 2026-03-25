package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.model.SensorAlertInput;
import com.algasensors.temperature.monitoring.api.model.SensorAlertOutput;
import com.algasensors.temperature.monitoring.common.IdGenerator;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors/")
public class SensorAlertController {

    private final SensorAlertRepository sensorAlertRepository;

    @GetMapping("{sensorId}/alert")
    ResponseEntity<SensorAlertOutput> getAlert(@PathVariable("sensorId") TSID sensorId) {

        SensorAlert sensorAlert = getSensorAlert(sensorId);

        return ResponseEntity.ok(convertToSensorAlertOutput(sensorAlert));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorAlertOutput createAlert(@RequestBody SensorAlertInput input) {
        SensorAlert sensorAlert = SensorAlert
                .builder()
                .id(new SensorId(IdGenerator.generateTSID()))
                .minTemperature(input.getMinTemperature())
                .maxTemperature(input.getMaxTemperature())
                .build();
        sensorAlertRepository.save(sensorAlert);
        return convertToSensorAlertOutput(sensorAlert);
    }

    @PutMapping("{sensorId}/alert")
    public void updateAlert(@PathVariable("sensorId") TSID sensorId,
                            @RequestBody SensorAlertInput input) {
        SensorAlert sensorAlert = getSensorAlert(sensorId);
        sensorAlert.setMaxTemperature(input.getMaxTemperature());
        sensorAlert.setMinTemperature(input.getMinTemperature());
        sensorAlertRepository.save(sensorAlert);
    }

    @DeleteMapping("{sensorId}/alert")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlert(@PathVariable("sensorId") TSID sensorId) {
        SensorAlert sensorAlert = getSensorAlert(sensorId);
        sensorAlertRepository.delete(sensorAlert);
    }

    private static SensorAlertOutput convertToSensorAlertOutput(SensorAlert sensorAlert) {
        return SensorAlertOutput.builder()
                .id(sensorAlert.getId().getValue())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }

    private @NonNull SensorAlert getSensorAlert(TSID sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

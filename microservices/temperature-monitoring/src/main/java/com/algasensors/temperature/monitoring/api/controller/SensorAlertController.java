package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorAlertResponseMapper;
import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.api.response.SensorAlertResponse;
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
    private final SensorAlertResponseMapper sensorAlertResponseMapper;


    @GetMapping("{sensorId}/alert")
    ResponseEntity<SensorAlertResponse> getAlert(@PathVariable("sensorId") TSID sensorId) {

        SensorAlert sensorAlert = getSensorAlert(sensorId);

        return ResponseEntity.ok(sensorAlertResponseMapper.toResponse(sensorAlert));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorAlertResponse createAlert(@RequestBody SensorAlertRequest input) {
        SensorAlert sensorAlert = getSensorAlert(input);
        sensorAlertRepository.save(sensorAlert);
        return sensorAlertResponseMapper.toResponse(sensorAlert);
    }

    private static SensorAlert getSensorAlert(SensorAlertRequest input) {
        SensorAlert sensorAlert = SensorAlert
                .builder()
                .id(new SensorId(IdGenerator.generateTSID()))
                .minTemperature(input.getMinTemperature())
                .maxTemperature(input.getMaxTemperature())
                .build();
        return sensorAlert;
    }

    @PutMapping("{sensorId}/alert")
    public void updateAlert(@PathVariable("sensorId") TSID sensorId,
                            @RequestBody SensorAlertRequest input) {
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


    private @NonNull SensorAlert getSensorAlert(TSID sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

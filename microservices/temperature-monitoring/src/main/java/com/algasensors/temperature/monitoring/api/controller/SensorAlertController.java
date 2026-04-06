package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorAlertResponseMapper;
import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.api.response.SensorAlertResponse;
import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors/")
public class SensorAlertController {

    private final SensorAlertGateway sensorAlertGateway;
    private final SensorAlertResponseMapper sensorAlertResponseMapper;


    @GetMapping("{sensorId}/alert")
    ResponseEntity<SensorAlertResponse> getAlertById(@PathVariable("sensorId") SensorId sensorId) {

        SensorAlert sensorAlert = getSensorAlert(sensorId);

        return ResponseEntity.ok(sensorAlertResponseMapper.toResponse(sensorAlert));
    }

    @GetMapping
    public Page<SensorAlertResponse> findAll(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return sensorAlertGateway.findAll(pageable).map(sensorAlertResponseMapper::toResponse);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorAlertResponse createAlert(@RequestBody SensorAlertRequest input) {
        SensorAlert sensorAlert = getSensorAlert(input);
        sensorAlertGateway.save(sensorAlert);
        return sensorAlertResponseMapper.toResponse(sensorAlert);
    }

    private static SensorAlert getSensorAlert(SensorAlertRequest input) {
        return SensorAlert
                .builder()
                .id(SensorId.generate())
                .minTemperature(input.getMinTemperature())
                .maxTemperature(input.getMaxTemperature())
                .build();
    }

    @PutMapping("{sensorId}/alert")
    public void updateAlert(@PathVariable("sensorId") SensorId sensorId,
                            @RequestBody SensorAlertRequest input) {
        SensorAlert sensorAlert = getSensorAlert(sensorId);
        sensorAlert.setMaxTemperature(input.getMaxTemperature());
        sensorAlert.setMinTemperature(input.getMinTemperature());
        sensorAlertGateway.save(sensorAlert);
    }

    @DeleteMapping("{sensorId}/alert")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlert(@PathVariable("sensorId") SensorId sensorId) {
        SensorAlert sensorAlert = getSensorAlert(sensorId);
        sensorAlertGateway.delete(sensorAlert);
    }


    private @NonNull SensorAlert getSensorAlert(SensorId sensorId) {
        return sensorAlertGateway.findById(new SensorId(sensorId.getValue()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

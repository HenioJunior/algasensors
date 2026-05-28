package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorAlertResponseMapper;
import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.api.response.SensorAlertResponse;
import com.algasensors.temperature.monitoring.application.usecase.alert.DeleteSensorAlertUseCase;
import com.algasensors.temperature.monitoring.application.usecase.alert.FindSensorAlertByIdUseCase;
import com.algasensors.temperature.monitoring.application.usecase.alert.impl.CreateOrUpdateOrUpdateSensorAlertUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/temperature-monitoring/sensors/{sensorId}/alert")
public class SensorAlertController {

    private final FindSensorAlertByIdUseCase findSensorAlertByIdUseCase;
    private final SensorAlertResponseMapper sensorAlertResponseMapper;
    private final CreateOrUpdateOrUpdateSensorAlertUseCase createOrUpdateSensorAlertUseCase;
    private final DeleteSensorAlertUseCase deleteSensorAlertUseCase;


    @GetMapping
    public ResponseEntity<SensorAlertResponse> getAlertById(@PathVariable("sensorId") SensorId sensorId) {
        SensorAlert sensorAlert = findSensorAlertByIdUseCase.execute(sensorId);
        return ResponseEntity.ok(sensorAlertResponseMapper.toResponse(sensorAlert));
    }

    @PutMapping
    public ResponseEntity<SensorAlertResponse> createOrUpdate(
            @PathVariable String sensorId,
            @RequestBody SensorAlertRequest request
    ) {
        SensorId id = SensorId.of(sensorId);

        SensorAlert alert = createOrUpdateSensorAlertUseCase.execute(id, request);

        return ResponseEntity.ok(sensorAlertResponseMapper.toResponse(alert));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlert(@PathVariable("sensorId") SensorId sensorId) {
        deleteSensorAlertUseCase.execute(sensorId);
    }
}

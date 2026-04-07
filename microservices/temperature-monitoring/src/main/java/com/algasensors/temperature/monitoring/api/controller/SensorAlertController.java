package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorAlertResponseMapper;
import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.api.response.SensorAlertResponse;
import com.algasensors.temperature.monitoring.application.usecase.alert.DeleteSensorAlertUseCase;
import com.algasensors.temperature.monitoring.application.usecase.alert.FindSensorAlertByIdUseCase;
import com.algasensors.temperature.monitoring.application.usecase.alert.UpdateSensorAlertUseCase;
import com.algasensors.temperature.monitoring.application.usecase.alert.impl.CreateSensorAlertUseCaseImpl;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors/{sensorId}/alert")
public class SensorAlertController {

    private final FindSensorAlertByIdUseCase findSensorAlertByIdUseCase;
    private final SensorAlertResponseMapper sensorAlertResponseMapper;
    private final CreateSensorAlertUseCaseImpl createSensorAlertUseCaseImpl;
    private final UpdateSensorAlertUseCase updateSensorAlertUseCase;
    private final DeleteSensorAlertUseCase deleteSensorAlertUseCase;


    @GetMapping
    public ResponseEntity<SensorAlertResponse> getAlertById(@PathVariable("sensorId") SensorId sensorId) {
        SensorAlert sensorAlert = findSensorAlertByIdUseCase.execute(sensorId);
        return ResponseEntity.ok(sensorAlertResponseMapper.toResponse(sensorAlert));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorAlertResponse createAlert(@PathVariable("sensorId") SensorId sensorId, @RequestBody SensorAlertRequest request) {
        SensorAlert sensorAlert = createSensorAlertUseCaseImpl.execute(sensorId, request);
        return sensorAlertResponseMapper.toResponse(sensorAlert);
    }

    @PutMapping
    public void updateAlert(@PathVariable("sensorId") SensorId sensorId,
                            @RequestBody SensorAlertRequest request) {
        updateSensorAlertUseCase.execute(sensorId, request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlert(@PathVariable("sensorId") SensorId sensorId) {
        deleteSensorAlertUseCase.execute(sensorId);
    }
}

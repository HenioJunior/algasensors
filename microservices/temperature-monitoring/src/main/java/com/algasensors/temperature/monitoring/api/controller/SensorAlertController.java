package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorAlertResponseMapper;
import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.api.response.SensorAlertResponse;
import com.algasensors.temperature.monitoring.application.usecase.FindSensorAlertByIdUseCase;
import com.algasensors.temperature.monitoring.application.usecase.FindSensorAlertsUseCase;
import com.algasensors.temperature.monitoring.application.usecase.UpdateSensorAlertUseCase;
import com.algasensors.temperature.monitoring.application.usecase.impl.CreateSensorAlertUseCase;
import com.algasensors.temperature.monitoring.application.usecase.impl.DeleteSensorAlertUseCase;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors/")
public class SensorAlertController {

    private final FindSensorAlertsUseCase findSensorAlertsUseCase;
    private final FindSensorAlertByIdUseCase findSensorAlertByIdUseCase;
    private final SensorAlertResponseMapper sensorAlertResponseMapper;
    private final CreateSensorAlertUseCase createSensorAlertUseCase;
    private final UpdateSensorAlertUseCase updateSensorAlertUseCase;
    private final DeleteSensorAlertUseCase deleteSensorAlertUseCase;


    @GetMapping("{sensorId}/alert")
    public ResponseEntity<SensorAlertResponse> getAlertById(@PathVariable("sensorId") SensorId sensorId) {
        SensorAlert sensorAlert = findSensorAlertByIdUseCase.execute(sensorId);
        return ResponseEntity.ok(sensorAlertResponseMapper.toResponse(sensorAlert));
    }

    @GetMapping
    public Page<SensorAlertResponse> getAlerts(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return findSensorAlertsUseCase.execute(pageable)
                .map(sensorAlertResponseMapper::toResponse);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorAlertResponse createAlert(@RequestBody SensorAlertRequest request) {
        SensorAlert sensorAlert = createSensorAlertUseCase.execute(request);
        return sensorAlertResponseMapper.toResponse(sensorAlert);
    }

    @PutMapping("{sensorId}/alert")
    public void updateAlert(@PathVariable("sensorId") SensorId sensorId,
                            @RequestBody SensorAlertRequest request) {
        updateSensorAlertUseCase.execute(sensorId, request);
    }

    @DeleteMapping("{sensorId}/alert")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlert(@PathVariable("sensorId") SensorId sensorId) {
        deleteSensorAlertUseCase.execute(sensorId);
    }
}

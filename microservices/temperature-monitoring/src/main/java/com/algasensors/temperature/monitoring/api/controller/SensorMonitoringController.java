package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorMonitoringResponseMapper;
import com.algasensors.temperature.monitoring.api.response.SensorMonitoringResponse;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.CreateMonitoringUseCase;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.DisableSensorMonitoringUseCase;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.EnableSensorMonitoringUseCase;
import com.algasensors.temperature.monitoring.application.usecase.monitoring.FindSensorMonitoringByIdUseCase;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
@RequiredArgsConstructor
public class SensorMonitoringController {

    private final FindSensorMonitoringByIdUseCase findSensorMonitoringByIdUseCase;
    private final CreateMonitoringUseCase createMonitoringUseCase;
    private final EnableSensorMonitoringUseCase enableSensorMonitoringUseCase;
    private final DisableSensorMonitoringUseCase disableSensorMonitoringUseCase;
    private final SensorMonitoringResponseMapper sensorMonitoringResponseMapper;

    @GetMapping
    public SensorMonitoringResponse getDetail(@PathVariable("sensorId") SensorId sensorId){
        SensorMonitoring sensorMonitoring = findSensorMonitoringByIdUseCase.execute(sensorId);
        return sensorMonitoringResponseMapper.toResponse(sensorMonitoring);
    }

    @PostMapping("/create")
    public SensorMonitoringResponse create(@PathVariable("sensorId") SensorId sensorId){
        SensorMonitoring sensorMonitoring = createMonitoringUseCase.execute(sensorId);
        return sensorMonitoringResponseMapper.toResponse(sensorMonitoring);
    }

    @PutMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable("sensorId") SensorId sensorId) {
        enableSensorMonitoringUseCase.execute(sensorId);
    }

    @DeleteMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SneakyThrows
    public void disable(@PathVariable("sensorId") SensorId sensorId) {
       disableSensorMonitoringUseCase.execute(sensorId);
    }
}

package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorMonitoringResponseMapper;
import com.algasensors.temperature.monitoring.api.response.SensorMonitoringResponse;
import com.algasensors.temperature.monitoring.application.usecase.FindSensorMonitoringByIdUseCase;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
@RequiredArgsConstructor
public class SensorMonitoringController {

    private final FindSensorMonitoringByIdUseCase findSensorMonitoringByIdUseCase;
    private final SensorMonitoringResponseMapper sensorMonitoringResponseMapper;
    private final SensorMonitoringRepository sensorMonitoringRepository;

    @GetMapping
    public SensorMonitoringResponse getDetail(@PathVariable("sensorId") TSID sensorId){
        SensorMonitoring sensorMonitoring = findSensorMonitoringByIdUseCase.execute(new SensorId(sensorId));
        return sensorMonitoringResponseMapper.toResponse(sensorMonitoring);
    }

    private SensorMonitoring findByIdOrDefault(TSID sensorId) {
        return sensorMonitoringRepository.findById(new SensorId(sensorId))
                .orElse(SensorMonitoring.builder()
                        .id(new SensorId(sensorId))
                        .enabled(false)
                        .lastTemperature(null)
                        .updatedAt(null)
                        .build());
    }

    @PutMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable("sensorId") TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);
        if(sensorMonitoring.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        sensorMonitoring.setEnabled(true);
        sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }

    @DeleteMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SneakyThrows
    public void disable(@PathVariable("sensorId") TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);
        if(!sensorMonitoring.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        sensorMonitoring.setEnabled(false);
        sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }
}

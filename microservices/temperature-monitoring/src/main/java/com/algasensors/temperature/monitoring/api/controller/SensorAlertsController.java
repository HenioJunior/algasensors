package com.algasensors.temperature.monitoring.api.controller;

import com.algasensors.temperature.monitoring.api.mapper.SensorAlertResponseMapper;
import com.algasensors.temperature.monitoring.api.response.SensorAlertResponse;
import com.algasensors.temperature.monitoring.application.usecase.alert.FindSensorAlertsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors/alerts")
@RequiredArgsConstructor
public class SensorAlertsController {

    private final FindSensorAlertsUseCase findSensorAlertsUseCase;
    private final SensorAlertResponseMapper sensorAlertResponseMapper;

    @GetMapping
    public Page<SensorAlertResponse> getAlerts(Pageable pageable) {
        return findSensorAlertsUseCase.execute(pageable)
                .map(sensorAlertResponseMapper::toResponse);
    }
}

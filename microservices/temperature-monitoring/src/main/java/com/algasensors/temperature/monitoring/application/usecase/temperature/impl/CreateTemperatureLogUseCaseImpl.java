package com.algasensors.temperature.monitoring.application.usecase.temperature.impl;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogResponse;
import com.algasensors.temperature.monitoring.application.gateway.TemperatureLogGateway;
import com.algasensors.temperature.monitoring.application.usecase.temperature.CreateTemperatureLogUseCase;
import com.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.algasensors.temperature.monitoring.domain.valueobject.TemperatureLogId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTemperatureLogUseCaseImpl implements CreateTemperatureLogUseCase {

    private final TemperatureLogGateway temperatureLogGateway;

    @Override
    @Transactional
    public TemperatureLog execute(TemperatureLogResponse temperatureLogResponse) {
        String logId = temperatureLogResponse.getId();
        if (logId == null) {
            throw new IllegalArgumentException("TemperatureLogData.id is required");
        }

        TemperatureLog temperatureLog = TemperatureLog.builder()
                .id(new TemperatureLogId(logId))
                .sensorId(temperatureLogResponse.getSensorId())
                .temperatureValue(temperatureLogResponse.getValue())
                .registeredAt(temperatureLogResponse.getRegisteredAt())
                .build();

        return temperatureLogGateway.save(temperatureLog);
    }
}

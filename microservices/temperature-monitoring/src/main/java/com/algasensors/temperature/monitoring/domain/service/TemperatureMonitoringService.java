package com.algasensors.temperature.monitoring.domain.service;


import com.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import com.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemperatureMonitoringService {

    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    @Transactional
    public void processTemperatureReading(TemperatureLogData temperatureLogData) {

        sensorMonitoringRepository.findById(temperatureLogData.getSensorId())
                .ifPresentOrElse(sensor -> handleSensorMonitoring(temperatureLogData, sensor),
                        () -> logIgnoredTemperature(temperatureLogData));

    }

    private void handleSensorMonitoring(TemperatureLogData temperatureLogData, SensorMonitoring sensor) {
        String logId = Optional.ofNullable(temperatureLogData.getId())
                .orElseThrow(() -> new IllegalArgumentException("TemperatureLogData.id is required"));

        if(sensor.isEnabled()) {
            sensor.setLastTemperature(temperatureLogData.getValue());
            sensor.setUpdatedAt(OffsetDateTime.now());
            sensorMonitoringRepository.saveAndFlush(sensor);

            TemperatureLog temperatureLog = TemperatureLog.builder()
                    .id(new TemperatureLogId(logId))
                    .sensorId(temperatureLogData.getSensorId())
                    .temperatureValue(temperatureLogData.getValue())
                    .registeredAt(temperatureLogData.getRegisteredAt())
                    .build();

            temperatureLogRepository.saveAndFlush(temperatureLog);
            log.info("Temperature Updated: SensorId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
        } else {
           logIgnoredTemperature(temperatureLogData);
        }
    }

    private void logIgnoredTemperature(TemperatureLogData temperatureLogData) {
        log.warn("Ignored temperature reading for sensor {}: {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
    }


}

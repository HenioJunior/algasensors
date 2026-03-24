package com.algasensors.temperature.monitoring.domain.service;

import com.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import com.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TemperatureMonitoringService {

    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    @Transactional
    public void processTemperatureReading(TemperatureLogData temperatureLogData) {
        sensorMonitoringRepository.findById(new SensorId(temperatureLogData.getSensorId()))
                .ifPresentOrElse(
                sensor -> handleSensorMonitoring(temperatureLogData, sensor),
                () -> logIgnoredTenperature(temperatureLogData));
    }

    private void handleSensorMonitoring(TemperatureLogData temperatureLogData, SensorMonitoring sensor) {
    if(sensor.isEnabled()) {
        sensor.setLastTemperature(temperatureLogData.getValue());
        sensor.setUpdatedAt(OffsetDateTime.now());
        sensorMonitoringRepository.save(sensor);

        TemperatureLog temperatureLog = TemperatureLog.builder()
                .id(new TemperatureLogId(temperatureLogData.getId()))
                .temperatureValue(temperatureLogData.getValue())
                .registeredAt(temperatureLogData.getRegisteredAt())
                .sensorId(new SensorId(temperatureLogData.getSensorId()))
                .build();

        temperatureLogRepository.save(temperatureLog);
    }


    }

    private void logIgnoredTenperature(TemperatureLogData temperatureLogData) {
    }
}

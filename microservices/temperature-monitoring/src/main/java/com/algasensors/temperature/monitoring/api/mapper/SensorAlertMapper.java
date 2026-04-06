package com.algasensors.temperature.monitoring.api.mapper;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.common.IdGenerator;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import org.springframework.stereotype.Component;

@Component
public class SensorAlertMapper {

    public SensorAlert getSensorAlert(SensorAlertRequest input) {
        return SensorAlert
                .builder()
                .sensorId(new SensorId(IdGenerator.generateTSID()))
                .minTemperature(input.getMinTemperature())
                .maxTemperature(input.getMaxTemperature())
                .build();
    }
}

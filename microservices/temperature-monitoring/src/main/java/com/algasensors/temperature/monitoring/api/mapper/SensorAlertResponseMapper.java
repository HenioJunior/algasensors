package com.algasensors.temperature.monitoring.api.mapper;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.api.response.SensorAlertResponse;
import com.algasensors.temperature.monitoring.common.IdGenerator;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.model.SensorId;
import org.springframework.stereotype.Component;

@Component
public class SensorAlertResponseMapper {

    public SensorAlert getSensorAlert(SensorAlertRequest input) {
        SensorAlert sensorAlert = SensorAlert
                .builder()
                .id(new SensorId(IdGenerator.generateTSID()))
                .minTemperature(input.getMinTemperature())
                .maxTemperature(input.getMaxTemperature())
                .build();
        return sensorAlert;

    }

    public SensorAlertResponse toResponse(SensorAlert sensorAlert) {
        return SensorAlertResponse.builder()
                .id(sensorAlert.getId())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }
}

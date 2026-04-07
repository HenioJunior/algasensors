package com.algasensors.temperature.monitoring.infra.config.web;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import org.springframework.core.convert.converter.Converter;


public class StringToSensorIdConverter implements Converter<String, SensorId> {
    @Override
    public SensorId convert(String source) {
        return SensorId.of(source);
    }
}

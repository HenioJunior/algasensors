package com.algasensors.temperature.processing.infra.kafka.sensor;

import com.algasensors.temperature.processing.api.model.SensorId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TemperatureMessage {

    @JsonProperty("sensorId")
    private SensorId sensorId;

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("timestamp")
    private String timestamp;

    @Override
    public String toString() {
        return "TemperatureMessage{" +
                "sensorId='" + sensorId + '\'' +
                ", temperature=" + temperature +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

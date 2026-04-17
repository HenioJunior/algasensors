package com.algasensors.temperature.processing.infra.kafka.sensor;

import com.algasensors.temperature.processing.api.model.SensorId;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TemperatureMessage {

    @JsonProperty("sensorId")
    private SensorId sensorId;

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("timestamp")
    private String timestamp;

    // Getters e Setters
    public SensorId getSensorId() {
        return sensorId;
    }

    public void setSensorId(SensorId sensorId) {
        this.sensorId = sensorId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TemperatureMessage{" +
                "sensorId='" + sensorId + '\'' +
                ", temperature=" + temperature +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

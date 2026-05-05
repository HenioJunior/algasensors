package com.algasensors.device.management.infra.client.impl;

import com.algasensors.device.management.domain.valueobject.SensorId;
import com.algasensors.device.management.infra.client.SensorClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SensorClientImpl implements SensorClient {

    private final RestClient restClient;

    public SensorClientImpl(@Qualifier("sensorRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void startTransmission(SensorId sensorId) {
        restClient.post()
                .uri("/api/sensor/sendTemperature/{sensorId}", sensorId.getValue())
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void stopTransmission(SensorId sensorId) {
        restClient.post()
                .uri("/api/sensor/stopSending/{sensorId}", sensorId.getValue())
                .retrieve()
                .toBodilessEntity();
    }
}

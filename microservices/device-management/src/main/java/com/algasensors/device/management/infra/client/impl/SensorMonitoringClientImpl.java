package com.algasensors.device.management.infra.client.impl;

import com.algasensors.device.management.api.response.SensorMonitoringResponse;
import com.algasensors.device.management.domain.valueobject.SensorId;
import com.algasensors.device.management.infra.client.SensorMonitoringClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    private final RestClient restClient;

    public SensorMonitoringClientImpl(RestClientFactory factory) {
        this.restClient = factory.temperatureMonitoringRestClient();
    }

    @Override
    public void enableMonitoring(SensorId sensorId) {
        restClient.put()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void disableMonitoring(SensorId sensorId) {
        restClient.delete()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public SensorMonitoringResponse getDetail(SensorId sensorId) {
        return restClient.get()
                .uri("/api/sensors/{sensorId}/monitoring", sensorId)
                .retrieve()
                .body(SensorMonitoringResponse.class);
    }

    @Override
    public void create(SensorId sensorId) {
        restClient.post().uri("/api/sensors/{sensorId}/monitoring/create", sensorId).retrieve().toBodilessEntity();
    }
}

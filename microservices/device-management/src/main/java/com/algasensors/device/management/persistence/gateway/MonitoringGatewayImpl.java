package com.algasensors.device.management.persistence.gateway;

import com.algasensors.device.management.application.gateway.MonitoringGateway;
import com.algasensors.device.management.domain.valueobject.SensorId;
import com.algasensors.device.management.infra.client.SensorMonitoringClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitoringGatewayImpl implements MonitoringGateway {

    private final SensorMonitoringClient sensorMonitoringClient;

    @Override
    public void create(SensorId sensorId) {
        sensorMonitoringClient.create(sensorId);
    }
}

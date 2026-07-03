package com.algasensors.device.management.application.gateway;

import com.algasensors.device.management.domain.valueobject.SensorId;

public interface MonitoringGateway {
    void create(SensorId sensorId);
}

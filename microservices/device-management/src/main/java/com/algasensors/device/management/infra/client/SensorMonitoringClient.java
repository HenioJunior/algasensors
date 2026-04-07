package com.algasensors.device.management.infra.client;

import com.algasensors.device.management.api.response.SensorMonitoringResponse;
import com.algasensors.device.management.domain.valueobject.SensorId;
import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {

    void enableMonitoring(SensorId sensorId);
    void disableMonitoring(SensorId sensorId);
    SensorMonitoringResponse getDetail(SensorId sensorId);

    void create(SensorId sensorId);
}

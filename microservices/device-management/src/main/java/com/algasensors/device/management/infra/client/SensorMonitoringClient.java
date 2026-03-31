package com.algasensors.device.management.infra.client;

import com.algasensors.device.management.api.response.SensorMonitoringOutput;
import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {

    void enableMonitoring(TSID sensorId);
    void disableMonitoring(TSID sensorId);
    SensorMonitoringOutput getDetail(TSID sensorId);
}

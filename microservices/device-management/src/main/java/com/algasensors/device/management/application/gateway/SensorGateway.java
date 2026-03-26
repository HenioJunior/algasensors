package com.algasensors.device.management.application.gateway;

import com.algasensors.device.management.domain.model.Sensor;

public interface SensorGateway {

    Sensor save(Sensor sensor);
}

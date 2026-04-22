package com.algasensors.temperature.processing.gateways;

import com.algasensors.temperature.processing.domain.model.TemperatureReading;
import com.algasensors.temperature.processing.domain.valueobject.SensorId;

public interface TemperatureTechnicalLogGateway {
    void saveReceived(TemperatureReading reading);
    void saveProcessed(TemperatureReading reading);
    void saveDiscarded(SensorId rawSensorId, String reason);
}

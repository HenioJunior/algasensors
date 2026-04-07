package com.algasensors.temperature.monitoring.application.usecase.alert;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;

public interface CreateSensorAlertUseCase {
    SensorAlert execute(SensorId sensorId, SensorAlertRequest request);
}

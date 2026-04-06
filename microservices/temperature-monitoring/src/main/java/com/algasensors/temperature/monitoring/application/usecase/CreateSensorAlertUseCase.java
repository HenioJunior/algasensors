package com.algasensors.temperature.monitoring.application.usecase;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;

public interface CreateSensorAlertUseCase {
    SensorAlert execute(SensorAlertRequest request);
}

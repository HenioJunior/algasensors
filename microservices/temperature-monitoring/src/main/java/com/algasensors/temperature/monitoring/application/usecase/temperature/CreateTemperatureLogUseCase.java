package com.algasensors.temperature.monitoring.application.usecase.temperature;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogResponse;
import com.algasensors.temperature.monitoring.domain.model.TemperatureLog;

public interface CreateTemperatureLogUseCase {
    TemperatureLog execute(TemperatureLogResponse temperatureLogResponse);
}

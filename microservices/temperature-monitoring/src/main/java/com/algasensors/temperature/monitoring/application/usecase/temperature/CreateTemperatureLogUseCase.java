package com.algasensors.temperature.monitoring.application.usecase.temperature;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogData;
import com.algasensors.temperature.monitoring.domain.model.TemperatureLog;

public interface CreateTemperatureLogUseCase {
    TemperatureLog execute(TemperatureLogData temperatureLogData);
}

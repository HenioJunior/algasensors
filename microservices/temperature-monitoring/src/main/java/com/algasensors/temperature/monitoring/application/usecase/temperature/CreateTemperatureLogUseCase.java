package com.algasensors.temperature.monitoring.application.usecase.temperature;

import com.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.algasensors.temperature.monitoring.domain.model.TemperatureReading;

public interface CreateTemperatureLogUseCase {
    TemperatureLog execute(TemperatureReading temperatureReading);
}

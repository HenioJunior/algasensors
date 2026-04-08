package com.algasensors.temperature.monitoring.application.usecase.temperature;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogData;

public interface ProcessTemperatureReadingUseCase {
    void execute(TemperatureLogData temperatureLogData);
}

package com.algasensors.temperature.monitoring.application.usecase.temperature;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogResponse;

public interface ProcessTemperatureReadingUseCase {
    void execute(TemperatureLogResponse temperatureLogResponse);
}

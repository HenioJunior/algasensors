package com.algasensors.temperature.monitoring.application.usecase.temperature;

import com.algasensors.temperature.monitoring.domain.model.TemperatureReading;

public interface ProcessTemperatureReadingUseCase {
    void execute(TemperatureReading temperatureReading);
}

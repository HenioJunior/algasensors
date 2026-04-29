package com.algasensors.temperature.processing.application;

import com.algasensors.temperature.processing.infra.messaging.dto.TemperatureMessage;

public interface ProcessTemperatureReadingUseCase {

    void execute(TemperatureMessage message);
}

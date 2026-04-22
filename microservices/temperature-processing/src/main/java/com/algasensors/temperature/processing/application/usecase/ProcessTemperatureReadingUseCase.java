package com.algasensors.temperature.processing.application.usecase;

import com.algasensors.temperature.processing.infra.messaging.dto.TemperatureMessage;

public interface ProcessTemperatureReadingUseCase {

    void execute(TemperatureMessage message);
}

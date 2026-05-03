package com.algasensors.temperature.processing.gateways;

import com.algasensors.temperature.processing.domain.model.TemperatureReading;
import com.algasensors.temperature.processing.domain.model.TemperatureTechnicalLog;

import java.time.Instant;

public interface TemperatureTechnicalLogGateway {

    TemperatureTechnicalLog saveReceived(String rawMessageId, TemperatureReading reading);

    void markAsProcessed(String technicalLogId, String processedEventId, Instant processedAt);

    void markAsFailed(String technicalLogId, String errorMessage);

}

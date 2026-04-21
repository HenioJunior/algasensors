package com.algasensors.temperature.processing.gateways;

import com.algasensors.temperature.processing.infra.messaging.event.TemperatureProcessedEvent;

public interface TemperatureProcessedEventPublisher {
    void publish(TemperatureProcessedEvent event);
}

package com.algasensors.temperature.processing.service;

import com.algasensors.temperature.processing.infra.kafka.sensor.TemperatureMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TemperatureProcessingService {

    @KafkaListener(topics = "temperature_topic", groupId = "temperature-processing-group")
    public void consumeTemperatureMessage(TemperatureMessage temperatureMessage) {
        System.out.println("Consumed temperature message: " + temperatureMessage);
        // Aqui você pode implementar o processamento da temperatura
    }
}

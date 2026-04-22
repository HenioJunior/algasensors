package com.algasensors.temperature.processing.infra.messaging.consumer;

import com.algasensors.temperature.processing.application.usecase.ProcessTemperatureReadingUseCase;
import com.algasensors.temperature.processing.infra.messaging.dto.TemperatureMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TemperatureRawConsumer {

    private final ProcessTemperatureReadingUseCase processTemperatureReadingUseCase;

    public TemperatureRawConsumer(ProcessTemperatureReadingUseCase processTemperatureReadingUseCase) {
        this.processTemperatureReadingUseCase = processTemperatureReadingUseCase;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.temperature-raw}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(TemperatureMessage message) {
        processTemperatureReadingUseCase.execute(message);
    }
}

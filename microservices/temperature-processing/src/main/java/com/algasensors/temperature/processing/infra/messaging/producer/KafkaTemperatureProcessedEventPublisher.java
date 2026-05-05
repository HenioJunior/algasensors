package com.algasensors.temperature.processing.infra.messaging.producer;

import com.algasensors.temperature.processing.gateways.TemperatureProcessedEventPublisher;
import com.algasensors.temperature.processing.infra.messaging.event.TemperatureProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaTemperatureProcessedEventPublisher implements TemperatureProcessedEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaTemperatureProcessedEventPublisher.class);

    private final KafkaTemplate<String, TemperatureProcessedEvent> kafkaTemplate;
    private final String topic;

    public KafkaTemperatureProcessedEventPublisher(
            KafkaTemplate<String, TemperatureProcessedEvent> kafkaTemplate,
            @Value("${app.kafka.topics.processed-reading}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(TemperatureProcessedEvent event) {
        String key = event.sensorId();

        System.out.println("[DEBUG_LOG] Publishing processed event to topic: " + topic + " with key: " + key);

        kafkaTemplate.send(topic, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.err.println("[DEBUG_LOG] Failed to publish processed event: " + ex.getMessage());
                        log.error(
                                "Failed to publish processed temperature event. topic={}, sensorId={}, eventId={}",
                                topic,
                                event.sensorId(),
                                event.eventId(),
                                ex
                        );
                    }

                    if (result != null && result.getRecordMetadata() != null) {
                        var metadata = result.getRecordMetadata();
                        System.out.println("[DEBUG_LOG] Event published successfully to partition " + metadata.partition() + " at offset " + metadata.offset());
                        log.info(
                                "Processed temperature event published. topic={}, partition={}, offset={}, sensorId={}, eventId={}",
                                metadata.topic(),
                                metadata.partition(),
                                metadata.offset(),
                                event.sensorId(),
                                event.eventId()
                        );
                    }
                });
    }
}

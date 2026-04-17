package com.algasensors.temperature.processing.infra.kafka.config;

import com.algasensors.temperature.processing.infra.kafka.sensor.TemperatureMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, TemperatureMessage> consumerFactory() {
        JsonDeserializer<TemperatureMessage> jsonDeserializer =
                new JsonDeserializer<>(TemperatureMessage.class);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setRemoveTypeHeaders(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "temperature-processing-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public MessageListenerContainer messageListenerContainer() {
        ContainerProperties containerProps = new ContainerProperties("temperature_topic");

        // Configuração do listener
        containerProps.setMessageListener(new MessageListener<String, TemperatureMessage>() {
            @Override
            public void onMessage(org.apache.kafka.clients.consumer.ConsumerRecord<String, TemperatureMessage> record) {
                TemperatureMessage message = record.value();
                System.out.println("Received: " + message);
                // Aqui você pode implementar o processamento da temperatura
            }
        });
        // Criando o container de escuta de mensagens para o tópico
        ConcurrentMessageListenerContainer<String, TemperatureMessage> container =
                new ConcurrentMessageListenerContainer<>(consumerFactory(), containerProps);
        return container;

    }
}

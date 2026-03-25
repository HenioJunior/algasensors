package com.algasensors.temperature.processing.api.controller;

import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.server.ResponseStatusException;


import static com.algasensors.temperature.processing.infra.rabbitmq.RabbitMQConfig.FANOUT_EXCHANGE_NAME;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TemperatureProcessingControllerTest {

    private RabbitTemplate rabbitTemplate;
    private TemperatureProcessingController controller;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        controller = new TemperatureProcessingController(rabbitTemplate);
    }

    @Test
    @DisplayName("Deve enviar temperatura válida para o RabbitMQ")
    void shouldSendTemperatureToRabbitMq() {
        TSID sensorId = TSID.fast();

        ArgumentCaptor<String> exchangeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        ArgumentCaptor<MessagePostProcessor> postProcessorCaptor = ArgumentCaptor.forClass(MessagePostProcessor.class);

        controller.data(sensorId, "26.7");

        verify(rabbitTemplate).convertAndSend(
                exchangeCaptor.capture(),
                routingKeyCaptor.capture(),
                payloadCaptor.capture(),
                postProcessorCaptor.capture()
        );

        assertThat(exchangeCaptor.getValue()).isEqualTo(FANOUT_EXCHANGE_NAME);
        assertThat(routingKeyCaptor.getValue()).isEqualTo("");
        assertThat(payloadCaptor.getValue()).isNotNull();

        Message message = new Message(new byte[0]);
        Message processedMessage = postProcessorCaptor.getValue().postProcessMessage(message);

        assertThat(processedMessage.getMessageProperties().getHeaders())
                .containsEntry("sensorId", sensorId.toString());
    }

    @Test
    @DisplayName("Deve lançar 400 quando input for nulo")
    void shouldThrow400WhenInputIsNull() {
        TSID sensorId = TSID.fast();

        assertThatThrownBy(() -> controller.data(sensorId, null))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(400);

        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), any(), any(MessagePostProcessor.class));
    }

    @Test
    @DisplayName("Deve lançar 400 quando input for vazio")
    void shouldThrow400WhenInputIsBlank() {
        TSID sensorId = TSID.fast();

        assertThatThrownBy(() -> controller.data(sensorId, "   "))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(400);

        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), any(), any(MessagePostProcessor.class));
    }

    @Test
    @DisplayName("Deve lançar 400 quando input não for numérico")
    void shouldThrow400WhenInputIsInvalid() {
        TSID sensorId = TSID.fast();

        assertThatThrownBy(() -> controller.data(sensorId, "abc"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode.value")
                .isEqualTo(400);

        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), any(), any(MessagePostProcessor.class));
    }

    @Test
    @DisplayName("Deve aceitar número decimal negativo")
    void shouldAcceptNegativeTemperature() {
        TSID sensorId = TSID.fast();

        controller.data(sensorId, "-5.2");

        verify(rabbitTemplate).convertAndSend(
                eq(FANOUT_EXCHANGE_NAME),
                eq(""),
                any(),
                any(MessagePostProcessor.class)
        );
    }
}

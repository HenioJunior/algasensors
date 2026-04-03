package com.algasensors.temperature.monitoring.infra.rabbitmq;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogData;
import com.algasensors.temperature.monitoring.domain.service.ProcessTemperatureAlertUseCase;
import com.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.algasensors.temperature.monitoring.infra.rabbitmq.RabbitMQConfig.QUEUE_PROCESS_TEMPERATURE;

@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitMQListener {

    private final TemperatureMonitoringService temperatureMonitoringService;
    private final ProcessTemperatureAlertUseCase sensorAlertService;

    @SneakyThrows
    @RabbitListener(queues = QUEUE_PROCESS_TEMPERATURE)
    public void handleProcessTemperature(@Payload TemperatureLogData temperatureLogData) {
        temperatureMonitoringService.processTemperatureReading(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));
    }
}

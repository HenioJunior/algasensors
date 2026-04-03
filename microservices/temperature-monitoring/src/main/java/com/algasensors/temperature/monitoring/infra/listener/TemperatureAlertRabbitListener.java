package com.algasensors.temperature.monitoring.infra.listener;

import com.algasensors.temperature.monitoring.api.response.TemperatureLogData;
import com.algasensors.temperature.monitoring.domain.service.AlertEvaluationResult;
import com.algasensors.temperature.monitoring.domain.service.ProcessTemperatureAlertUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemperatureAlertRabbitListener {

    private final ProcessTemperatureAlertUseCase processTemperatureAlertUseCase;

    @RabbitListener(queues = "${app.rabbitmq.queues.temperature-alert}")
    public void handle(TemperatureLogData temperatureLogData) {
        AlertEvaluationResult result = processTemperatureAlertUseCase.execute(temperatureLogData);

        if (result.isMaxExceeded()) {
            log.info("Max temperature exceeded - sensorId={} current={} max={}",
                    result.getSensorId(),
                    result.getCurrentTemperature(),
                    result.getMaxTemperature());
            return;
        }

        if (result.isMinExceeded()) {
            log.info("Min temperature exceeded - sensorId={} current={} min={}",
                    result.getSensorId(),
                    result.getCurrentTemperature(),
                    result.getMinTemperature());
            return;
        }

        log.info("Ignored alert - sensorId={} current={}",
                temperatureLogData.getSensorId(),
                temperatureLogData.getValue());
    }
}

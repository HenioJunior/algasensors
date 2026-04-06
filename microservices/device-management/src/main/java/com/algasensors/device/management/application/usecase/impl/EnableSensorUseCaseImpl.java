package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.EnableSensorUseCase;
import com.algasensors.device.management.application.support.SensorIdParser;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import com.algasensors.device.management.infra.client.impl.SensorMonitoringClientImpl;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnableSensorUseCaseImpl implements EnableSensorUseCase {

    private final SensorGateway sensorGateway;
    private final SensorMonitoringClientImpl sensorMonitoringClient;
    private final SensorIdParser sensorIdParser;

    @Override
    public void execute(Command command) {
        SensorId sensorId = sensorIdParser.parse(command.sensorId());

        Sensor sensor = sensorGateway.findById(sensorId)
                .orElseThrow(() -> new SensorNotFoundException(command.sensorId()));

        sensor.enable();
        sensorGateway.save(sensor);

        TSID tsid = sensorId.getId();
        sensorMonitoringClient.enableMonitoring(tsid);
    }
}

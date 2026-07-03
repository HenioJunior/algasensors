package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.FindSensorByIdUseCase;
import com.algasensors.device.management.domain.exception.InvalidSensorIdException;
import com.algasensors.device.management.domain.exception.SensorNotFoundException;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.valueobject.SensorId;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindSensorByIdUseCaseImpl implements FindSensorByIdUseCase {

    private final SensorGateway sensorGateway;

    @Override
    public Sensor execute(Query query) {
        final TSID tsid;

        try {
            tsid = TSID.from(query.sensorId());
        } catch (Exception ex) {
            throw new InvalidSensorIdException(query.sensorId());
        }

        SensorId sensorId = new SensorId(tsid);

        return sensorGateway.findById(sensorId)
                .orElseThrow(() -> new SensorNotFoundException(query.sensorId()));
    }

}

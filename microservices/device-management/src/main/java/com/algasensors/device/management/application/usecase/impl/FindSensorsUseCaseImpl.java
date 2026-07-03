package com.algasensors.device.management.application.usecase.impl;

import com.algasensors.device.management.application.gateway.SensorGateway;
import com.algasensors.device.management.application.usecase.FindSensorsUseCase;
import com.algasensors.device.management.domain.model.Sensor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindSensorsUseCaseImpl implements FindSensorsUseCase {

    private final SensorGateway sensorGateway;

    @Override
    public Page<Sensor> execute(Pageable pageable) {
        return sensorGateway.findAll(pageable);
    }
}

package com.algasensors.device.management.application.usecase;

import com.algasensors.device.management.domain.model.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindSensorsUseCase {
    Page<Sensor> execute(Pageable pageable);
}

package com.algasensors.temperature.monitoring.application.usecase.alert;

import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindSensorAlertsUseCase {
    Page<SensorAlert> execute(Pageable pageable);
}

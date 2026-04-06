package com.algasensors.temperature.monitoring.api.response;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class SensorMonitoringResponse {
    private SensorId id;
    private BigDecimal lastTemperature;
    private OffsetDateTime updatedAt;
    private boolean enabled;
}

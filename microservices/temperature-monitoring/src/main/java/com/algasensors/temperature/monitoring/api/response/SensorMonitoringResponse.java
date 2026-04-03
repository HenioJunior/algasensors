package com.algasensors.temperature.monitoring.api.response;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class SensorMonitoringResponse {
    private TSID id;
    private BigDecimal lastTemperature;
    private OffsetDateTime updatedAt;
    private boolean enabled;
}

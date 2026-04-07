package com.algasensors.device.management.api.response;

import com.algasensors.device.management.domain.valueobject.SensorId;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorMonitoringResponse {
    private SensorId id;
    private Double lastTemperature;
    private OffsetDateTime updatedAt;
    private Boolean enabled;
}

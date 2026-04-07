package com.algasensors.temperature.monitoring.domain.model;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.common.IdGenerator;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorAlert {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id", columnDefinition = "BIGINT"))
    private SensorId sensorId;
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;

    public static SensorAlert execute(SensorId sensorId, SensorAlertRequest request) {
        return SensorAlert
                .builder()
                .sensorId(sensorId)
                .minTemperature(request.getMinTemperature())
                .maxTemperature(request.getMaxTemperature())
                .build();
    }

    public void updateTemperatureRange(BigDecimal minTemperature, BigDecimal maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }
}

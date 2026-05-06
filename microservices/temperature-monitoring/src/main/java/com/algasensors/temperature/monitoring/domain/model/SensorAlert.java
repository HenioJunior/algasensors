package com.algasensors.temperature.monitoring.domain.model;

import com.algasensors.temperature.monitoring.api.request.SensorAlertRequest;
import com.algasensors.temperature.monitoring.common.IdGenerator;
import com.algasensors.temperature.monitoring.domain.exception.InvalidSensorAlertRangeException;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorAlert {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false, updatable = false))
    private SensorId sensorId;
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;

    public static SensorAlert create(SensorId sensorId, BigDecimal minTemperature, BigDecimal maxTemperature) {
        return SensorAlert
                .builder()
                .sensorId(sensorId)
                .minTemperature(minTemperature)
                .maxTemperature(maxTemperature)
                .build();
    }

    public SensorAlert update(BigDecimal minTemperature, BigDecimal maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        return this;
    }

    private void validateRange(BigDecimal minTemperature, BigDecimal maxTemperature) {
        Objects.requireNonNull(minTemperature);
        Objects.requireNonNull(maxTemperature);

        if (minTemperature.compareTo(maxTemperature) >= 0) {
            throw new InvalidSensorAlertRangeException();
        }
    }
}

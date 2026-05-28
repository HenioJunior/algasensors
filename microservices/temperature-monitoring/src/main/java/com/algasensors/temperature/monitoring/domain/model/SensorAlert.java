package com.algasensors.temperature.monitoring.domain.model;

import com.algasensors.temperature.monitoring.domain.exception.InvalidSensorAlertRangeException;
import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorAlert {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false, updatable = false))
    private SensorId sensorId;
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;
    private BigDecimal warningMinTemperature;
    private BigDecimal warningMaxTemperature;

    public static SensorAlert create(SensorId sensorId, BigDecimal minTemperature, BigDecimal maxTemperature, BigDecimal warningMinTemperature, BigDecimal warningMaxTemperature) {
        validateRange(
                minTemperature,
                maxTemperature,
                warningMinTemperature,
                warningMaxTemperature
        );
        return SensorAlert
                .builder()
                .sensorId(Objects.requireNonNull(sensorId))
                .minTemperature(minTemperature)
                .maxTemperature(maxTemperature)
                .warningMinTemperature(warningMinTemperature)
                .warningMaxTemperature(warningMaxTemperature)
                .build();
    }

    public SensorAlert update(BigDecimal minTemperature, BigDecimal maxTemperature, BigDecimal warningMinTemperature, BigDecimal warningMaxTemperature) {
        validateRange(
                minTemperature,
                maxTemperature,
                warningMinTemperature,
                warningMaxTemperature
        );

        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.warningMinTemperature = warningMinTemperature;
        this.warningMaxTemperature = warningMaxTemperature;
        return this;
    }

    private static void validateRange(
            BigDecimal minTemperature,
            BigDecimal maxTemperature,
            BigDecimal warningMinTemperature,
            BigDecimal warningMaxTemperature
    ) {

        Objects.requireNonNull(minTemperature);
        Objects.requireNonNull(maxTemperature);
        Objects.requireNonNull(warningMinTemperature);
        Objects.requireNonNull(warningMaxTemperature);

        boolean invalid =
                minTemperature.compareTo(warningMinTemperature) >= 0 ||
                        warningMinTemperature.compareTo(warningMaxTemperature) >= 0 ||
                        warningMaxTemperature.compareTo(maxTemperature) >= 0;

        if (invalid) {
            throw new InvalidSensorAlertRangeException();
        }
    }
}

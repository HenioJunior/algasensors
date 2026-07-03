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
    private BigDecimal minTemperature;
    private BigDecimal warningMinTemperature;
    private BigDecimal warningMaxTemperature;
    private BigDecimal maxTemperature;

    public static SensorAlert create(SensorId sensorId, BigDecimal minTemperature, BigDecimal warningMinTemperature, BigDecimal warningMaxTemperature, BigDecimal maxTemperature) {
        validateRange(
                minTemperature,
                warningMinTemperature,
                warningMaxTemperature,
                maxTemperature

        );
        return SensorAlert
                .builder()
                .sensorId(Objects.requireNonNull(sensorId))
                .minTemperature(minTemperature)
                .warningMinTemperature(warningMinTemperature)
                .warningMaxTemperature(warningMaxTemperature)
                .maxTemperature(maxTemperature)
                .build();
    }

    public SensorAlert update(BigDecimal minTemperature, BigDecimal warningMinTemperature, BigDecimal warningMaxTemperature, BigDecimal maxTemperature) {
        validateRange(
                minTemperature,
                warningMinTemperature,
                warningMaxTemperature,
                maxTemperature
        );

        this.minTemperature = minTemperature;
        this.warningMinTemperature = warningMinTemperature;
        this.warningMaxTemperature = warningMaxTemperature;
        this.maxTemperature = maxTemperature;
        return this;
    }

    private static void validateRange(
            BigDecimal minTemperature,
            BigDecimal warningMinTemperature,
            BigDecimal warningMaxTemperature,
            BigDecimal maxTemperature
    ) {

        Objects.requireNonNull(minTemperature);
        Objects.requireNonNull(warningMinTemperature);
        Objects.requireNonNull(warningMaxTemperature);
        Objects.requireNonNull(maxTemperature);

        boolean invalid =
                minTemperature.compareTo(warningMinTemperature) >= 0 ||
                        warningMinTemperature.compareTo(warningMaxTemperature) >= 0 ||
                        warningMaxTemperature.compareTo(maxTemperature) >= 0;

        if (invalid) {
            throw new InvalidSensorAlertRangeException();
        }
    }
}

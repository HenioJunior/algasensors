package com.algasensors.temperature.monitoring.domain.model;

import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorAlert {

    @Id
    @AttributeOverride(
            name = "id",
            column = @Column(name = "id", columnDefinition = "BIGINT")
    )
    private SensorId sensorId;
    private BigDecimal maxTemperature;
    private BigDecimal minTemperature;

    public void updateTemperatureRange(BigDecimal minTemperature, BigDecimal maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }
}

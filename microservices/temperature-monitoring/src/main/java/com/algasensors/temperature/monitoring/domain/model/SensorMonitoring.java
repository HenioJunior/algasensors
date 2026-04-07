package com.algasensors.temperature.monitoring.domain.model;


import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorMonitoring {
    @Id
    @AttributeOverride(name = "id", column = @Column(name="id", columnDefinition = "BIGINT"))
    private SensorId id;
    private BigDecimal lastTemperature;
    private OffsetDateTime updatedAt;
    private boolean enabled;

    public void enable() {
        if (Boolean.TRUE.equals(this.enabled)) {
            return;
        }
        this.enabled = Boolean.TRUE;
    }

    public void disable() {
        if (Boolean.FALSE.equals(this.enabled)) {
            return;
        }
        this.enabled = Boolean.FALSE;
    }
}

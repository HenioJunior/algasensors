package com.algasensors.temperature.monitoring.domain.model;


import com.algasensors.temperature.monitoring.domain.valueobject.SensorId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
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

    public static SensorMonitoring create(SensorId id) {
        return SensorMonitoring.builder()
                .id(id)
                .lastTemperature(BigDecimal.valueOf(0.0))
                .updatedAt(OffsetDateTime.now())
                .enabled(Boolean.TRUE).build();
    }

    public static void enable(SensorMonitoring sensorMonitoring) {
        if (sensorMonitoring.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        sensorMonitoring.enabled = Boolean.TRUE;
    }

    public static void disable(SensorMonitoring sensorMonitoring) {
        if (!sensorMonitoring.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        sensorMonitoring.enabled = Boolean.FALSE;
    }
}

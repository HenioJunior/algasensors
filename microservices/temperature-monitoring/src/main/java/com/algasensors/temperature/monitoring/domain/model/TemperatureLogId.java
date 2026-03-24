package com.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class TemperatureLogId implements Serializable {

    private UUID value;

    public TemperatureLogId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("TemperatureLogId cannot be null");
        }
        this.value = value;
    }

    public TemperatureLogId(String value) {
        this(UUID.fromString(value));
    }


    @Override
    public String toString() {
        return value.toString();
    }
}

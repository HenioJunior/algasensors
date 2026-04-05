package com.algasensors.device.management.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SensorId implements Serializable {

    private TSID sensorValue;

    public SensorId(TSID sensorValue) {
        Objects.requireNonNull(sensorValue);
        this.sensorValue = sensorValue;
    }

    public SensorId(Long sensorValue) {
        Objects.requireNonNull(sensorValue);
        this.sensorValue = TSID.from(sensorValue);
    }

    public SensorId(String sensorValue) {
        Objects.requireNonNull(sensorValue);
        this.sensorValue = TSID.from(sensorValue);
    }

    @Override
    public String toString() {
        return sensorValue.toString();
    }
}

package com.algasensors.temperature.monitoring.domain.model;

import com.algasensors.temperature.monitoring.common.IdGenerator;
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

    private TSID value;

    public SensorId(TSID value) {
        this.value = Objects.requireNonNull(value);;
    }

    public static SensorId of(TSID value) {
        return new SensorId(value);
    }

    public static SensorId of(long value) {
        return new SensorId(TSID.from(value));
    }

    public static SensorId generate() {
        return new SensorId(IdGenerator.generateTSID());
    }

    public TSID getValue() {
        return value;
    }

    public long toLong() {
        return value.toLong();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

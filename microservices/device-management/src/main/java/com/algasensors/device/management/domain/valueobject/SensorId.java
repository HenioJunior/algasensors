package com.algasensors.device.management.domain.valueobject;

import com.algasensors.device.management.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
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

    @Column(name = "id", nullable = false, updatable = false)
    protected String value;

    public SensorId(String value) {
        this.value = value;
    }

    public SensorId(TSID value) {
        this.value = Objects.requireNonNull(value).toString();
    }

    public static SensorId of(long value) {
        return new SensorId(TSID.from(value));
    }

    public static SensorId of(String value) {
        return new SensorId(value);
    }

    public static SensorId generate() {
        return new SensorId(IdGenerator.generateTSID());
    }

    @Override
    public String toString() {
        return value;
    }
}

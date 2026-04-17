package com.algasensors.temperature.processing.api.model;


import com.algasensors.temperature.processing.common.IdGenerator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.hypersistence.tsid.TSID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SensorId implements Serializable {

    protected String value;

    public SensorId(String value) {
        this.value = value;
    }

    public SensorId(TSID value) {
        this.value = Objects.requireNonNull(value).toString();
    }

    public static SensorId of(String value) {
        return new SensorId(value);
    }

    public static SensorId generate() {
        return new SensorId(IdGenerator.generateTSID());
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

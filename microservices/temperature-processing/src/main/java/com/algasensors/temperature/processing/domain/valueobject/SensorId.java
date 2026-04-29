package com.algasensors.temperature.processing.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class SensorId implements Serializable {

    @Column(name = "id", nullable = false, updatable = false)
    protected String value;

    protected SensorId() {
    }

    private SensorId(String value) {
        this.value = value;
    }

    public static SensorId of(String value) {
        return new SensorId(value);
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

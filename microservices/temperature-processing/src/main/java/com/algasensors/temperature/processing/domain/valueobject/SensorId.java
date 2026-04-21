package com.algasensors.temperature.processing.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public class SensorId implements Serializable {

    protected String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

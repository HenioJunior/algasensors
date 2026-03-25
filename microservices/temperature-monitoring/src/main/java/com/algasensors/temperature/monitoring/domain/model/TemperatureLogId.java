package com.algasensors.temperature.monitoring.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class TemperatureLogId implements Serializable {

    @Column(name = "id", nullable = false, updatable = false)
    private String value;

    public TemperatureLogId(TSID value) {
        this.value = value.toString();
    }

    public TemperatureLogId(String value) {
        this.value = value;
    }

    public TSID toTSID() {
        return TSID.from(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

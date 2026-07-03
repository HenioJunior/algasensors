package com.algasensors.temperature.monitoring.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@MappedSuperclass
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseTsidId implements Serializable {

    @Column(name = "id", nullable = false, updatable = false)
    protected String value;

    protected BaseTsidId(TSID value) {
        this.value = Objects.requireNonNull(value).toString();
    }

    protected BaseTsidId(String value) {
        this.value = Objects.requireNonNull(value);
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

package com.algasensors.temperature.monitoring.domain.valueobject;

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

    public TSID toTSID() {
        return TSID.from(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

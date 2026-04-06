package com.algasensors.temperature.monitoring.domain.valueobject;

import com.algasensors.temperature.monitoring.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorId extends BaseTsidId {

    public SensorId(TSID value) {
        super(value);
    }

    public SensorId(String value) {
        super(value);
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
}

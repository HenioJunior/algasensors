package com.algasensors.temperature.monitoring.domain.valueobject;

import com.algasensors.temperature.monitoring.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class TemperatureLogId extends BaseTsidId {

    public TemperatureLogId(TSID value) {
        super(value);
    }

    public TemperatureLogId(String value) {
        super(value);
    }

    public static TemperatureLogId of(long value) {
        return new TemperatureLogId(TSID.from(value));
    }

    public static TemperatureLogId of(String value) {
        return new TemperatureLogId(value);
    }

    public static TemperatureLogId generate() {
        return new TemperatureLogId(IdGenerator.generateTSID());
    }
}

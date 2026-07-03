package com.algasensors.device.management.domain.model;

import com.algasensors.device.management.domain.valueobject.SensorId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sensor {

    @EmbeddedId
    private SensorId id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String protocol;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Boolean enabled;

    public static Sensor create(
            SensorId id,
            String name,
            String location,
            String ip,
            String protocol,
            String model
    ) {
        return Sensor.builder()
                .id(id)
                .name(name)
                .location(location)
                .ip(ip)
                .protocol(protocol)
                .model(model)
                .enabled(Boolean.FALSE)
                .build();
    }

    public void update(
            String name,
            String location,
            String ip,
            String protocol,
            String model
    ) {
        this.name = name;
        this.location = location;
        this.ip = ip;
        this.protocol = protocol;
        this.model = model;
    }

    public void enable() {
        if (Boolean.TRUE.equals(this.enabled)) {
            return;
        }
        this.enabled = Boolean.TRUE;
    }

    public void disable() {
        if (Boolean.FALSE.equals(this.enabled)) {
            return;
        }
        this.enabled = Boolean.FALSE;
    }
}

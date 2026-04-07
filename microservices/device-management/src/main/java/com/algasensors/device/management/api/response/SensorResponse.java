package com.algasensors.device.management.api.response;

import com.algasensors.device.management.domain.valueobject.SensorId;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SensorResponse {

    private SensorId id;
    private String name;
    private String ip;
    private String location;
    private String protocol;
    private String model;
    private Boolean enabled;
}

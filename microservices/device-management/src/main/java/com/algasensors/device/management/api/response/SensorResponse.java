package com.algasensors.device.management.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SensorResponse {

    private String id;
    private String name;
    private String ip;
    private String location;
    private String protocol;
    private String model;
    private Boolean enabled;
}

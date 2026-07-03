package com.algasensors.temperature.monitoring.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SensorMonitoringRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotBlank
    private String ip;

    @NotBlank
    private String protocol;

    @NotBlank
    private String model;
}

package com.algasensors.device.management.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSensorRequest {

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

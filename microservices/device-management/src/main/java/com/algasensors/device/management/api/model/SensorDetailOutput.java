package com.algasensors.device.management.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorDetailOutput {
    private SensorOutput sensor;
    private SensorMonitoringOutput monitoring;
}

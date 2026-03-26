package com.algasensors.device.management.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorDetailOutput {
    private SensorResponse sensor;
    private SensorMonitoringOutput monitoring;
}

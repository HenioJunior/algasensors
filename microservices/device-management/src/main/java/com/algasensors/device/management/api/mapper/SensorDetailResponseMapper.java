package com.algasensors.device.management.api.mapper;

import com.algasensors.device.management.api.response.SensorDetailResponse;
import com.algasensors.device.management.application.usecase.FindSensorDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SensorDetailResponseMapper {

    private final SensorResponseMapper sensorResponseMapper;

    public SensorDetailResponse toResponse(FindSensorDetailUseCase.Result result) {
        return SensorDetailResponse.builder()
                .sensor(sensorResponseMapper.toResponse(result.sensor()))
                .monitoring(result.monitoring())
                .build();
    }
}

package com.algasensors.device.management.api.controller;

import com.algasensors.device.management.api.mapper.SensorDetailResponseMapper;
import com.algasensors.device.management.api.mapper.SensorResponseMapper;
import com.algasensors.device.management.api.request.CreateSensorRequest;
import com.algasensors.device.management.api.response.SensorDetailResponse;
import com.algasensors.device.management.api.response.SensorResponse;
import com.algasensors.device.management.application.usecase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final CreateSensorUseCase createSensorUseCase;
    private final FindSensorByIdUseCase findSensorByIdUseCase;
    private final FindSensorDetailUseCase findSensorDetailUseCase;
    private final FindSensorsUseCase findSensorsUseCase;
    private final UpdateSensorUseCase updateSensorUseCase;
    private final EnableSensorUseCase enableSensorUseCase;
    private final DisableSensorUseCase disableSensorUseCase;
    private final DeleteSensorUseCase deleteSensorUseCase;
    private final SensorResponseMapper sensorResponseMapper;
    private final SensorDetailResponseMapper sensorDetailResponseMapper;

    @GetMapping
    public Page<SensorResponse> getSensors(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return findSensorsUseCase.execute(pageable)
                .map(sensorResponseMapper::toResponse);
    }

    @GetMapping("/{sensorId}")
    public SensorResponse getSensorById(@PathVariable("sensorId") String sensorId) {
        var query = new FindSensorByIdUseCase.Query(sensorId);
        var sensor = findSensorByIdUseCase.execute(query);
        return sensorResponseMapper.toResponse(sensor);
    }

    @GetMapping("/{sensorId}/detail")
    public SensorDetailResponse findDetailById(@PathVariable("sensorId") String sensorId) {
        var query = new FindSensorDetailUseCase.Query(sensorId);
        var result = findSensorDetailUseCase.execute(query);
        return sensorDetailResponseMapper.toResponse(result);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorResponse create(@RequestBody CreateSensorRequest request) {
        var command = new CreateSensorUseCase.CreateSensorCommand(
                request.getName(),
                request.getLocation(),
                request.getIp(),
                request.getProtocol(),
                request.getModel()
        );

        var sensor = createSensorUseCase.execute(command);

        return sensorResponseMapper.toResponse(sensor);
    }

    @PutMapping("/{sensorId}")
    public SensorResponse update(@PathVariable("sensorId") String sensorId,
                                 @Valid @RequestBody CreateSensorRequest request) {
        var command = new UpdateSensorUseCase.Command(
                sensorId,
                request.getName(),
                request.getLocation(),
                request.getIp(),
                request.getProtocol(),
                request.getModel()
        );

        var sensor = updateSensorUseCase.execute(command);
        return sensorResponseMapper.toResponse(sensor);
    }

    @PutMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable("sensorId") String sensorId) {
        enableSensorUseCase.execute(new EnableSensorUseCase.Command(sensorId));
    }

    @DeleteMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable("sensorId") String sensorId) {
        disableSensorUseCase.execute(new DisableSensorUseCase.Command(sensorId));
    }

    @DeleteMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("sensorId") String sensorId) {
        deleteSensorUseCase.execute(new DeleteSensorUseCase.Command(sensorId));
    }

}

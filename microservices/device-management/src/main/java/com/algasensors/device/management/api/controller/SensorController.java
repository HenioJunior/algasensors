package com.algasensors.device.management.api.controller;

import com.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algasensors.device.management.api.mapper.SensorResponseMapper;
import com.algasensors.device.management.api.response.SensorDetailOutput;
import com.algasensors.device.management.api.response.SensorMonitoringOutput;
import com.algasensors.device.management.api.response.SensorResponse;
import com.algasensors.device.management.application.usecase.CreateSensorUseCase;
import com.algasensors.device.management.domain.model.Sensor;
import com.algasensors.device.management.domain.model.SensorId;
import com.algasensors.device.management.api.request.CreateSensorRequest;
import com.algasensors.device.management.common.IdGenerator;
import com.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final CreateSensorUseCase createSensorUseCase;
    private final SensorResponseMapper sensorResponseMapper;

    private final SensorRepository sensorRepository;
    private final SensorMonitoringClient sensorMonitoringClient;

    @GetMapping
    public Page<SensorResponse> search(@PageableDefault Pageable pageable){
        Page<Sensor> sensors = sensorRepository.findAll(pageable);
        return sensors.map(this::convertToSensorOutput);
    }

    @GetMapping("{sensorId}")
    public SensorResponse getOne(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToSensorOutput(sensor);
    }

    @GetMapping("{sensorId}/detail")
    public SensorDetailOutput getOneWithDetail(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SensorMonitoringOutput detail = sensorMonitoringClient.getDetail(sensorId);
        SensorResponse sensorResponse = convertToSensorOutput(sensor);

        return SensorDetailOutput.builder()
                .sensor(sensorResponse)
                .monitoring(detail)
                .build();
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
    public SensorResponse update(@PathVariable("sensorId") TSID sensorId,
                                 @RequestBody CreateSensorRequest input) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        sensor.setName(input.getName());
        sensor.setLocation(input.getLocation());
        sensor.setIp(input.getIp());
        sensor.setModel(input.getModel());
        sensor.setProtocol(input.getProtocol());

        sensor = sensorRepository.save(sensor);

        return convertToSensorOutput(sensor);
    }

    @PutMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setEnabled(Boolean.TRUE);
        sensorRepository.save(sensor);
        sensorMonitoringClient.enableMonitoring(sensorId);
    }

    @DeleteMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setEnabled(Boolean.FALSE);
        sensorRepository.save(sensor);
        sensorMonitoringClient.disableMonitoring(sensorId);
    }

    @DeleteMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensorRepository.delete(sensor);
    }

    private SensorResponse convertToSensorOutput(Sensor sensor) {
        return SensorResponse.builder()
                .id(sensor.getId().getValue().toString())
                .name(sensor.getName())
                .ip(sensor.getIp())
                .location(sensor.getLocation())
                .protocol(sensor.getProtocol())
                .model(sensor.getModel())
                .enabled(sensor.getEnabled())
                .build();
    }
}

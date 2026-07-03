package com.algasensors.device.management.api.controller;

import com.algasensors.device.management.api.mapper.SensorDetailResponseMapper;
import com.algasensors.device.management.api.mapper.SensorResponseMapper;
import com.algasensors.device.management.api.request.CreateSensorRequest;
import com.algasensors.device.management.api.response.SensorDetailResponse;
import com.algasensors.device.management.api.response.SensorResponse;
import com.algasensors.device.management.application.usecase.*;
import com.algasensors.device.management.domain.model.Sensor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorControllerTest {

    @Mock
    private CreateSensorUseCase createSensorUseCase;
    @Mock
    private FindSensorByIdUseCase findSensorByIdUseCase;
    @Mock
    private FindSensorDetailUseCase findSensorDetailUseCase;
    @Mock
    private FindSensorsUseCase findSensorsUseCase;
    @Mock
    private UpdateSensorUseCase updateSensorUseCase;
    @Mock
    private EnableSensorUseCase enableSensorUseCase;
    @Mock
    private DisableSensorUseCase disableSensorUseCase;
    @Mock
    private DeleteSensorUseCase deleteSensorUseCase;
    @Mock
    private SensorResponseMapper sensorResponseMapper;
    @Mock
    private SensorDetailResponseMapper sensorDetailResponseMapper;

    @InjectMocks
    private SensorController sensorController;

    @Mock
    private Sensor sensor;

    @Mock
    private SensorResponse sensorResponse;

    @Mock
    private SensorDetailResponse sensorDetailResponse;

    @Test
    void shouldGetSensorsSuccessfully() {
        Pageable pageable = PageRequest.of(0, 20);
        Sensor anotherSensor = mock(Sensor.class);
        SensorResponse anotherResponse = mock(SensorResponse.class);

        Page<Sensor> sensorsPage = new PageImpl<>(List.of(sensor, anotherSensor), pageable, 2);

        when(findSensorsUseCase.execute(pageable)).thenReturn(sensorsPage);
        when(sensorResponseMapper.toResponse(sensor)).thenReturn(sensorResponse);
        when(sensorResponseMapper.toResponse(anotherSensor)).thenReturn(anotherResponse);

        Page<SensorResponse> result = sensorController.getSensors(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(sensorResponse, result.getContent().get(0));
        assertEquals(anotherResponse, result.getContent().get(1));

        verify(findSensorsUseCase).execute(pageable);
        verify(sensorResponseMapper).toResponse(sensor);
        verify(sensorResponseMapper).toResponse(anotherSensor);
        verifyNoMoreInteractions(findSensorsUseCase, sensorResponseMapper);
    }

    @Test
    void shouldGetSensorByIdSuccessfully() {
        String sensorId = "sensor-123";

        when(findSensorByIdUseCase.execute(any(FindSensorByIdUseCase.Query.class))).thenReturn(sensor);
        when(sensorResponseMapper.toResponse(sensor)).thenReturn(sensorResponse);

        SensorResponse result = sensorController.getSensorById(sensorId);

        assertNotNull(result);
        assertEquals(sensorResponse, result);

        ArgumentCaptor<FindSensorByIdUseCase.Query> captor =
                ArgumentCaptor.forClass(FindSensorByIdUseCase.Query.class);

        verify(findSensorByIdUseCase).execute(captor.capture());
        assertEquals(sensorId, captor.getValue().sensorId());

        verify(sensorResponseMapper).toResponse(sensor);
        verifyNoMoreInteractions(findSensorByIdUseCase, sensorResponseMapper);
    }

    @Test
    void shouldFindDetailByIdSuccessfully() {
        String sensorId = "sensor-123";
        FindSensorDetailUseCase.Result detailResult = mock(FindSensorDetailUseCase.Result.class);

        when(findSensorDetailUseCase.execute(any(FindSensorDetailUseCase.Query.class))).thenReturn(detailResult);
        when(sensorDetailResponseMapper.toResponse(detailResult)).thenReturn(sensorDetailResponse);

        SensorDetailResponse result = sensorController.findDetailById(sensorId);

        assertNotNull(result);
        assertEquals(sensorDetailResponse, result);

        ArgumentCaptor<FindSensorDetailUseCase.Query> captor =
                ArgumentCaptor.forClass(FindSensorDetailUseCase.Query.class);

        verify(findSensorDetailUseCase).execute(captor.capture());
        assertEquals(sensorId, captor.getValue().sensorId());

        verify(sensorDetailResponseMapper).toResponse(detailResult);
        verifyNoMoreInteractions(findSensorDetailUseCase, sensorDetailResponseMapper);
    }

    @Test
    void shouldCreateSensorSuccessfully() {
        CreateSensorRequest request = mock(CreateSensorRequest.class);
        when(request.getName()).thenReturn("Sensor A");
        when(request.getLocation()).thenReturn("Sala 01");
        when(request.getIp()).thenReturn("192.168.0.10");
        when(request.getProtocol()).thenReturn("MQTT");
        when(request.getModel()).thenReturn("Model X");

        when(createSensorUseCase.execute(any(CreateSensorUseCase.CreateSensorCommand.class))).thenReturn(sensor);
        when(sensorResponseMapper.toResponse(sensor)).thenReturn(sensorResponse);

        SensorResponse result = sensorController.create(request);

        assertNotNull(result);
        assertEquals(sensorResponse, result);

        ArgumentCaptor<CreateSensorUseCase.CreateSensorCommand> captor =
                ArgumentCaptor.forClass(CreateSensorUseCase.CreateSensorCommand.class);

        verify(createSensorUseCase).execute(captor.capture());

        CreateSensorUseCase.CreateSensorCommand command = captor.getValue();
        assertEquals("Sensor A", command.name());
        assertEquals("Sala 01", command.location());
        assertEquals("192.168.0.10", command.ip());
        assertEquals("MQTT", command.protocol());
        assertEquals("Model X", command.model());

        verify(sensorResponseMapper).toResponse(sensor);
        verifyNoMoreInteractions(createSensorUseCase, sensorResponseMapper);
    }

    @Test
    void shouldUpdateSensorSuccessfully() {
        String sensorId = "sensor-123";

        CreateSensorRequest request = mock(CreateSensorRequest.class);
        when(request.getName()).thenReturn("Sensor A");
        when(request.getLocation()).thenReturn("Sala 01");
        when(request.getIp()).thenReturn("192.168.0.10");
        when(request.getProtocol()).thenReturn("MQTT");
        when(request.getModel()).thenReturn("Model X");

        when(updateSensorUseCase.execute(any(UpdateSensorUseCase.Command.class))).thenReturn(sensor);
        when(sensorResponseMapper.toResponse(sensor)).thenReturn(sensorResponse);

        SensorResponse result = sensorController.update(sensorId, request);

        assertNotNull(result);
        assertEquals(sensorResponse, result);

        ArgumentCaptor<UpdateSensorUseCase.Command> captor =
                ArgumentCaptor.forClass(UpdateSensorUseCase.Command.class);

        verify(updateSensorUseCase).execute(captor.capture());

        UpdateSensorUseCase.Command command = captor.getValue();
        assertEquals(sensorId, command.sensorId());
        assertEquals("Sensor A", command.name());
        assertEquals("Sala 01", command.location());
        assertEquals("192.168.0.10", command.ip());
        assertEquals("MQTT", command.protocol());
        assertEquals("Model X", command.model());

        verify(sensorResponseMapper).toResponse(sensor);
        verifyNoMoreInteractions(updateSensorUseCase, sensorResponseMapper);
    }

    @Test
    void shouldEnableSensorSuccessfully() {
        String sensorId = "sensor-123";

        sensorController.enable(sensorId);

        ArgumentCaptor<EnableSensorUseCase.Command> captor =
                ArgumentCaptor.forClass(EnableSensorUseCase.Command.class);

        verify(enableSensorUseCase).execute(captor.capture());
        assertEquals(sensorId, captor.getValue().sensorId());

        verifyNoMoreInteractions(enableSensorUseCase);
    }

    @Test
    void shouldDisableSensorSuccessfully() {
        String sensorId = "sensor-123";

        sensorController.disable(sensorId);

        ArgumentCaptor<DisableSensorUseCase.Command> captor =
                ArgumentCaptor.forClass(DisableSensorUseCase.Command.class);

        verify(disableSensorUseCase).execute(captor.capture());
        assertEquals(sensorId, captor.getValue().sensorId());

        verifyNoMoreInteractions(disableSensorUseCase);
    }

    @Test
    void shouldDeleteSensorSuccessfully() {
        String sensorId = "sensor-123";

        sensorController.delete(sensorId);

        ArgumentCaptor<DeleteSensorUseCase.Command> captor =
                ArgumentCaptor.forClass(DeleteSensorUseCase.Command.class);

        verify(deleteSensorUseCase).execute(captor.capture());
        assertEquals(sensorId, captor.getValue().sensorId());

        verifyNoMoreInteractions(deleteSensorUseCase);
    }
}
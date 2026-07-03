package com.algasensors.temperature.monitoring.application.usecase.impl.alert;

import com.algasensors.temperature.monitoring.application.gateway.SensorAlertGateway;
import com.algasensors.temperature.monitoring.application.usecase.alert.impl.FindSensorAlertsUseCaseImpl;
import com.algasensors.temperature.monitoring.domain.model.SensorAlert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindSensorAlertsUseCaseImplTest {

    @Mock
    private SensorAlertGateway sensorAlertGateway;

    @InjectMocks
    private FindSensorAlertsUseCaseImpl useCase;

    private Pageable pageable;
    private Page<SensorAlert> page;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 20, Sort.by("sensorId"));
        page = new PageImpl<>(java.util.List.of(mock(SensorAlert.class), mock(SensorAlert.class)), pageable, 2);
    }

    @Test
    void shouldReturnPagedSensorAlerts() {
        when(sensorAlertGateway.findAll(pageable)).thenReturn(page);

        Page<SensorAlert> result = useCase.execute(pageable);

        assertSame(page, result);
        verify(sensorAlertGateway).findAll(pageable);
        verifyNoMoreInteractions(sensorAlertGateway);
    }
}

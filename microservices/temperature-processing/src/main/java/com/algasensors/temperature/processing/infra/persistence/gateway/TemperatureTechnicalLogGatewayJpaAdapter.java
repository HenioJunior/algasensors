package com.algasensors.temperature.processing.infra.persistence.gateway;

import com.algasensors.temperature.processing.domain.model.TemperatureReading;
import com.algasensors.temperature.processing.domain.valueobject.SensorId;
import com.algasensors.temperature.processing.gateways.TemperatureTechnicalLogGateway;
import com.algasensors.temperature.processing.infra.persistence.entity.TemperatureTechnicalLogEntity;
import com.algasensors.temperature.processing.infra.persistence.repository.TemperatureTechnicalLogJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class TemperatureTechnicalLogGatewayJpaAdapter implements TemperatureTechnicalLogGateway {

    private final TemperatureTechnicalLogJpaRepository repository;

    public TemperatureTechnicalLogGatewayJpaAdapter(TemperatureTechnicalLogJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveReceived(TemperatureReading reading) {
        TemperatureTechnicalLogEntity received = TemperatureTechnicalLogEntity
                .received(reading.getSensorId(), reading.getTemperature(), reading.getUnit(), reading.getTimestamp());
        repository.save(received);
    }

    @Override
    public void saveProcessed(TemperatureReading reading) {
        TemperatureTechnicalLogEntity processed = TemperatureTechnicalLogEntity.processed(
                reading.getSensorId(),
                reading.getTemperature(),
                reading.getUnit(),
                reading.getTimestamp());
        repository.save(processed);
    }

    @Override
    public void saveDiscarded(SensorId rawSensorId, String reason) {
        TemperatureTechnicalLogEntity discarded = TemperatureTechnicalLogEntity.discarded(rawSensorId, reason);
        repository.save(discarded);
    }

}

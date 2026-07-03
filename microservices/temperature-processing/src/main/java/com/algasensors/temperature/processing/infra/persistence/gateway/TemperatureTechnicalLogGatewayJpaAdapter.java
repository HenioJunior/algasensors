package com.algasensors.temperature.processing.infra.persistence.gateway;

import com.algasensors.temperature.processing.domain.model.TemperatureReading;
import com.algasensors.temperature.processing.domain.model.TemperatureTechnicalLog;
import com.algasensors.temperature.processing.gateways.TemperatureTechnicalLogGateway;
import com.algasensors.temperature.processing.infra.persistence.entity.TemperatureTechnicalLogEntity;
import com.algasensors.temperature.processing.infra.persistence.repository.TemperatureTechnicalLogJpaRepository;
import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class TemperatureTechnicalLogGatewayJpaAdapter implements TemperatureTechnicalLogGateway {

    private final TemperatureTechnicalLogJpaRepository repository;

    public TemperatureTechnicalLogGatewayJpaAdapter(TemperatureTechnicalLogJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public TemperatureTechnicalLog saveReceived(String rawMessageId, TemperatureReading reading) {
        TemperatureTechnicalLog domain = TemperatureTechnicalLog.received(rawMessageId, reading);
        domain.setId(TSID.fast().toString());

        TemperatureTechnicalLogEntity entity = TemperatureTechnicalLogEntity.fromDomain(domain);
        repository.save(entity);

        return domain;
    }

    @Override
    @Transactional
    public void markAsProcessed(String technicalLogId, String processedEventId, Instant processedAt) {
        TemperatureTechnicalLogEntity entity = findById(technicalLogId);

        TemperatureTechnicalLog domain = entity.toDomain();
        domain.markAsProcessed(processedEventId, processedAt);

        repository.save(TemperatureTechnicalLogEntity.fromDomain(domain));
    }

    @Override
    @Transactional
    public void markAsFailed(String technicalLogId, String errorMessage) {
        TemperatureTechnicalLogEntity entity = findById(technicalLogId);

        TemperatureTechnicalLog domain = entity.toDomain();
        domain.markAsFailed(errorMessage);

        repository.save(TemperatureTechnicalLogEntity.fromDomain(domain));
    }

    private TemperatureTechnicalLogEntity findById(String technicalLogId) {
        return repository.findById(technicalLogId)
                .orElseThrow(() -> new IllegalStateException(
                        "Technical log not found: " + technicalLogId
                ));
    }
}

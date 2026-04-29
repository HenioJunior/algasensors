package com.algasensors.temperature.processing.infra.persistence.repository;

import com.algasensors.temperature.processing.infra.persistence.entity.TemperatureTechnicalLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureTechnicalLogJpaRepository extends JpaRepository<TemperatureTechnicalLogEntity, String> {
}

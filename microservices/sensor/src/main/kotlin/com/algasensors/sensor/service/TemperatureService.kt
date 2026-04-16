package com.algasensors.sensor.service

import org.springframework.stereotype.Service

@Service
class TemperatureService {

    fun generateTemperature(): Double {
        // Gerar temperatura aleatória entre 18 e 30 graus Celsius
        return 18 + (Math.random() * (30 - 18))
    }
}
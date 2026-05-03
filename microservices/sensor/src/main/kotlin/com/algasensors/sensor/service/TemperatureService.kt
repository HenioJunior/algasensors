package com.algasensors.sensor.service

import com.algasensors.sensor.kafka.TemperatureProducer
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Service
import kotlin.concurrent.thread

@Service
class TemperatureService(
    private val temperatureProducer: TemperatureProducer
) {
    // Flag para controlar o loop de envio
    private var isSending = true

    fun initiateTemperatureTransmission(sensorId: String): String {
        if (isSending) {
            // Iniciar o envio em loop
            thread(start = true) {
                while (isSending) {
                    val temperature = generateTemperature()
                    temperatureProducer.sendTemperature(sensorId, temperature)
                    println("Temperature sent: $temperature")
                    Thread.sleep(3000) // Espera 30 segundos
                }
            }
            return "Temperature sending started"
        }
        return "Temperature sending is already running"
    }

    private fun generateTemperature(): Double {
        // Gerar temperatura aleatória entre 18 e 30 graus Celsius
        return 18 + (Math.random() * (30 - 18))
    }

    fun stopTemperatureTransmission() {
        isSending = false
    }
}
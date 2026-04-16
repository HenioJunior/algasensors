package com.algasensors.sensor.controller

import com.algasensors.sensor.kafka.TemperatureProducer
import com.algasensors.sensor.service.TemperatureService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/sensor/")
class TemperatureController(
    private val temperatureProducer: TemperatureProducer,
    private val temperatureService: TemperatureService
) {
    // Flag para controlar o loop de envio
    private var isSending = false

    @PostMapping("sendTemperature/{sensorId}")
    fun sendTemperature(@PathVariable sensorId: String): String {
        if (!isSending) {
            // Iniciar o envio em loop
            isSending = true
            thread(start = true) {
                while (isSending) {
                    val temperature = temperatureService.generateTemperature()
                    temperatureProducer.sendTemperature(sensorId, temperature)
                    println("Temperature sent: $temperature")
                    Thread.sleep(3000) // Espera 30 segundos
                }
            }
            return "Temperature sending started"
        }
        return "Temperature sending is already running"
    }

    @PostMapping("stopSending")
    fun stopSending(): String {
        isSending = false
        return "Temperature sending stopped"
    }
}
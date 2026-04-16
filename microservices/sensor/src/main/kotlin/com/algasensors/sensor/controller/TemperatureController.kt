package com.algasensors.sensor.controller

import com.algasensors.sensor.kafka.TemperatureProducer
import com.algasensors.sensor.service.TemperatureService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TemperatureController(
    private val temperatureProducer: TemperatureProducer,
    private val temperatureService: TemperatureService
) {

    @PostMapping("/sendTemperature/{sensorId}")
    fun sendTemperature(@PathVariable sensorId: String): String {
        val temperature = temperatureService.generateTemperature()
        temperatureProducer.sendTemperature(sensorId, temperature)
        return "Temperature sent: $temperature"
    }
}
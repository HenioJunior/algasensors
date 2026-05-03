package com.algasensors.sensor.controller

import com.algasensors.sensor.service.TemperatureService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sensor/")
class TemperatureController(
    private val temperatureService: TemperatureService
) {
    // Flag para controlar o loop de envio
    private var isSending = false

    @PostMapping("sendTemperature/{sensorId}")
    fun sendTemperature(@PathVariable sensorId: String): String {
        return temperatureService.initiateTemperatureTransmission(sensorId)
    }


    @PostMapping("stopSending")
    fun stopSending(): String {
       temperatureService.stopTemperatureTransmission()
        return "Temperature sending stopped"
    }
}
package com.algasensors.sensor.controller

import com.algasensors.sensor.service.TemperatureService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sensor")
class TemperatureController(
    private val temperatureService: TemperatureService
) {

    @PostMapping("/sendTemperature/{sensorId}")
    fun sendTemperature(@PathVariable sensorId: String): String {
        return temperatureService.initiateTemperatureTransmission(sensorId)
    }

    @PostMapping("/stopSending")
    fun stopSending(): String {
        return temperatureService.stopTemperatureTransmission()
    }

    @PostMapping("/stopSending/{sensorId}")
    fun stopSending(@PathVariable sensorId: String): String {
        return temperatureService.stopTemperatureTransmission(sensorId)
    }

    @GetMapping("/activeSensors")
    fun getActiveSensors(): List<String> {
        return temperatureService.getActiveSensors()
    }
}
package com.algasensors.sensor.service

import com.algasensors.sensor.kafka.TemperatureProducer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Service
class TemperatureService(
    private val temperatureProducer: TemperatureProducer
) {
    private val logger = LoggerFactory.getLogger(TemperatureService::class.java)
    private val scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors())
    private val sensorTasks = ConcurrentHashMap<String, ScheduledFuture<*>>()

    fun initiateTemperatureTransmission(sensorId: String): String {
        if (sensorTasks.containsKey(sensorId)) {
            return "Temperature sending is already running for sensor $sensorId"
        }

        val task = scheduler.scheduleAtFixedRate({
            try {
                val temperature = generateTemperature()
                temperatureProducer.sendTemperature(sensorId, temperature)
            } catch (e: Exception) {
                logger.error("Error sending temperature for sensor: {}", sensorId, e)
            }
        }, 0, 3, TimeUnit.SECONDS)

        sensorTasks[sensorId] = task
        logger.info("Started temperature transmission for sensor: {}", sensorId)
        return "Temperature sending started for sensor $sensorId"
    }

    private fun generateTemperature(): Double {
        // Gerar temperatura aleatória entre 18 e 30 graus Celsius
        return Random.nextDouble(0.0, 100.0)
    }

    fun stopTemperatureTransmission(sensorId: String? = null): String {
        return if (sensorId != null) {
            val task = sensorTasks.remove(sensorId)
            if (task != null) {
                task.cancel(false)
                logger.info("Stopped temperature transmission for sensor: {}", sensorId)
                "Temperature sending stopped for sensor $sensorId"
            } else {
                "No active transmission found for sensor $sensorId"
            }
        } else {
            val count = sensorTasks.size
            sensorTasks.forEach { (id, task) ->
                task.cancel(false)
                logger.info("Stopped temperature transmission for sensor: {}", id)
            }
            sensorTasks.clear()
            "Stopped transmissions for all $count sensors"
        }
    }

    fun getActiveSensors(): List<String> {
        return sensorTasks.keys().toList()
    }
}
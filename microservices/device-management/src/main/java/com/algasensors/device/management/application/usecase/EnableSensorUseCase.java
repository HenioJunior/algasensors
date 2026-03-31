package com.algasensors.device.management.application.usecase;

public interface EnableSensorUseCase {

    void execute(Command command);

    record Command(String sensorId) {
    }
}

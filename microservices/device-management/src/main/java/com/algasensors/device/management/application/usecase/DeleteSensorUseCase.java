package com.algasensors.device.management.application.usecase;

public interface DeleteSensorUseCase {

    void execute(Command command);

    record Command(String sensorId) {
    }
}

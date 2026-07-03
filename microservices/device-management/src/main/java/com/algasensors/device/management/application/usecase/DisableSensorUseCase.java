package com.algasensors.device.management.application.usecase;

public interface DisableSensorUseCase {

    void execute(Command command);

    record Command(String sensorId) {
    }
}

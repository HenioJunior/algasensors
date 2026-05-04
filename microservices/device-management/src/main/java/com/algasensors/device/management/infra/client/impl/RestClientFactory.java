package com.algasensors.device.management.infra.client.impl;

import com.algasensors.device.management.domain.exception.SensorMonitoringNotFoundException;
import com.algasensors.device.management.domain.exception.SensorMonitoringClientBadGatewayException;
import com.algasensors.device.management.domain.valueobject.SensorId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

    private final RestClient.Builder builder;

    public RestClient temperatureMonitoringRestClient() {
        return builder.baseUrl("http://localhost:8083")
                .requestFactory(generateClientRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, ((request, response) -> {
                    if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                        String sensorIdValue = (String) request.getAttributes().get("sensorId");
                        SensorId sensorId = sensorIdValue != null ? SensorId.of(sensorIdValue) : null;
                        throw new SensorMonitoringNotFoundException(sensorId);
                    }
                    throw new SensorMonitoringClientBadGatewayException();
                }))
                .build();
    }

    public RestClient sensorRestClient() {
        return builder.baseUrl("http://localhost:8082")
                .requestFactory(generateClientRequestFactory())
                .build();
    }

    private ClientHttpRequestFactory generateClientRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setReadTimeout(Duration.ofSeconds(4));
        factory.setConnectTimeout(Duration.ofSeconds(3));

        return factory;
    }
}

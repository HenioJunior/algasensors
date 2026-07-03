package com.algasensors.device.management.infra.config.http;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    @Qualifier("temperatureMonitoringClient")
    public RestClient temperatureMonitoringRestClient(
            RestClient.Builder builder,
            @Value("${app.temperature.monitoring.url}") String baseUrl
    ) {
        return builder
                .baseUrl(baseUrl)
                .requestFactory(generateClientRequestFactory())
                .build();
    }

    @Bean
    @Qualifier("sensorRestClient")
    public RestClient sensorRestClient(
            RestClient.Builder builder,
            @Value("${app.sensor.url}") String baseUrl
    ) {
        return builder
                .baseUrl(baseUrl)
                .requestFactory(generateClientRequestFactory())
                .build();
    }

    private ClientHttpRequestFactory generateClientRequestFactory() {

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(2))
                .setResponseTimeout(Timeout.ofSeconds(5))
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
}

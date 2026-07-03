CREATE TABLE sensor_alert (
                              id VARCHAR(50) NOT NULL,
                              max_temperature NUMERIC(10,2),
                              min_temperature NUMERIC(10,2),
                              CONSTRAINT sensor_alert_pk PRIMARY KEY (id)
);
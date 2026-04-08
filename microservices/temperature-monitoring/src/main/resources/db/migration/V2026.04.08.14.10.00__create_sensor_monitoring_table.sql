CREATE TABLE sensor_monitoring (
                                   id VARCHAR(50) NOT NULL,
                                   last_temperature NUMERIC(10,2),
                                   updated_at TIMESTAMP WITH TIME ZONE,
                                   enabled BOOLEAN NOT NULL,
                                   CONSTRAINT sensor_monitoring_pk PRIMARY KEY (id)
);
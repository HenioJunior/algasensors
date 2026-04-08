CREATE TABLE temperature_log (
                                 id VARCHAR(50) NOT NULL,
                                 temperature_value NUMERIC(10,2),
                                 registered_at TIMESTAMP WITH TIME ZONE,
                                 sensor_id VARCHAR(50) NOT NULL,
                                 CONSTRAINT temperature_log_pk PRIMARY KEY (id)
);
CREATE TABLE temperature_technical_log (
    id VARCHAR(36) NOT NULL PRIMARY KEY,

    raw_message_id VARCHAR(36) NOT NULL,
    processed_event_id VARCHAR(36),

    sensor_id VARCHAR(26) NOT NULL,
    temperature NUMERIC(10,2) NOT NULL,
    unit VARCHAR(10) NOT NULL,

    occurred_at TIMESTAMP NOT NULL,
    received_at TIMESTAMP NOT NULL,
    processed_at TIMESTAMP,

    status VARCHAR(30) NOT NULL,
    error_message VARCHAR(255)
);
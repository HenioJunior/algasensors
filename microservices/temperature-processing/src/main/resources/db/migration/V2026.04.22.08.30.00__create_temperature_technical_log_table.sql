CREATE TABLE temperature_technical_log (
                                           id VARCHAR(36) NOT NULL PRIMARY KEY,
                                           sensor_id VARCHAR(26),
                                           temperature NUMERIC(10,2),
                                           unit VARCHAR(10),
                                           occurred_at TIMESTAMP,
                                           logged_at TIMESTAMP NOT NULL,
                                           status VARCHAR(30) NOT NULL,
                                           reason VARCHAR(255)
);
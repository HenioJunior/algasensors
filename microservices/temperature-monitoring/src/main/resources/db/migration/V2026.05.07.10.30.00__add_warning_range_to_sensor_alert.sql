ALTER TABLE sensor_alert
    ADD COLUMN warning_min_temperature NUMERIC(10,2),
    ADD COLUMN warning_max_temperature NUMERIC(10,2);

UPDATE sensor_alert
SET
    warning_min_temperature = min_temperature,
    warning_max_temperature = max_temperature;

ALTER TABLE sensor_alert
    ALTER COLUMN warning_min_temperature SET NOT NULL,
    ALTER COLUMN warning_max_temperature SET NOT NULL;
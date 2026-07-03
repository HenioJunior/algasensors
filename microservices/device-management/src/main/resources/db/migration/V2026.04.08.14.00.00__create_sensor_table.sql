CREATE TABLE sensor (
                        id VARCHAR(50) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        ip VARCHAR(255) NOT NULL,
                        location VARCHAR(255) NOT NULL,
                        protocol VARCHAR(100) NOT NULL,
                        model VARCHAR(255) NOT NULL,
                        enabled BOOLEAN NOT NULL,
                        CONSTRAINT pk_sensor PRIMARY KEY (id)
);
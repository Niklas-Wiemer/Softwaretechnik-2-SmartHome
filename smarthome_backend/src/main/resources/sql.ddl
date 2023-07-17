CREATE TABLE device
(
    device_id               BIGINT NOT NULL PRIMARY KEY,
    ip                      VARCHAR(255),
    device_name             VARCHAR(255),
    room                    VARCHAR(255),
    device_purpose          VARCHAR(255),
    device_type             VARCHAR(255),
    state                   BOOLEAN,
    electricity_basic_usage DOUBLE PRECISION,
    water_basic_usage       DOUBLE PRECISION,
    electricity_basic_production DOUBLE PRECISION,
    runtime                 DOUBLE PRECISION
);
CREATE TABLE device_electricity_production_per_hour
(
    id                             BIGINT NOT NULL,
    device_id                      BIGINT,
    date                           varchar,
    average_electricity_production DOUBLE PRECISION,
    peak_electricity_production    DOUBLE PRECISION,
    CONSTRAINT pk_deviceelectricityproductionperhour PRIMARY KEY (id)
);


ALTER TABLE device_electricity_production_per_hour
    ADD CONSTRAINT FK_deviceelectricityproductionperhour FOREIGN KEY (device_id) REFERENCES device (device_id);

CREATE TABLE device_run_stats
(
    id                        BIGINT NOT NULL,
    device_id                 BIGINT,
    date                      varchar,
    average_electricity_usage DOUBLE PRECISION,
    average_water_usage       DOUBLE PRECISION,
    electricity_usage_for_run DOUBLE PRECISION,
    water_usage_for_run       DOUBLE PRECISION,
    cost_of_the_run           DOUBLE PRECISION,
    CONSTRAINT pk_devicerunstats PRIMARY KEY (id)
);

ALTER TABLE device_run_stats
    ADD CONSTRAINT FK_devicerustats FOREIGN KEY (device_id) REFERENCES device (device_id);

CREATE TABLE schedule
(
    schedule_id     BIGINT  NOT NULL,
    device_id       BIGINT  NOT NULL,
    schedule_beginn varchar NOT NULL,
    schedule_end    varchar NOT NULL,
    CONSTRAINT pk_schedule PRIMARY KEY (schedule_id)
);

ALTER TABLE schedule
    ADD CONSTRAINT FK_schedule_ON_DEVICE FOREIGN KEY (device_id) REFERENCES device (device_id);

CREATE TABLE tarif_entity
(
    tarif_id          BIGINT NOT NULL,
    time              VARCHAR(255),
    date              VARCHAR(255),
    electricity_price DOUBLE PRECISION,
    water_price       DOUBLE PRECISION,
    CONSTRAINT tarifentity PRIMARY KEY (tarif_id)
);

CREATE SEQUENCE DEVICE_SEQ
    START WITH 1
    INCREMENT BY 50;
CREATE SEQUENCE DEVICE_ELECTRICITY_PRODUCTION_PER_HOUR_SEQ
    START WITH 1
    INCREMENT BY 50;
CREATE SEQUENCE DEVICE_RUN_STATS_SEQ
    START WITH 1
    INCREMENT BY 50;
CREATE SEQUENCE SCHEDULE_SEQ
    START WITH 1
    INCREMENT BY 50;
CREATE SEQUENCE TARIF_ENTITY_SEQ
    START WITH 1
    INCREMENT BY 50;
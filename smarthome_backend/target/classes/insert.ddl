INSERT INTO Device (DEVICE_ID,ip, device_name, room, device_purpose, device_type, state, electricity_basic_usage, water_basic_usage, electricity_basic_production, runtime)
VALUES (1,'192.168.1.100', 'Smart DISHWASHER', 'Kitchen Room', 'CONSUMER', 'DISHWASHER', false, 200.0, 25.0,0.0, 10.0);
INSERT INTO Device (DEVICE_ID,ip, device_name, room, device_purpose, device_type, state, electricity_basic_usage, water_basic_usage, electricity_basic_production, runtime)
VALUES (2,'192.168.1.101', 'Smart WASHING_MACHINE', 'Kitchen Room', 'CONSUMER', 'WASHING_MACHINE', true, 250.0, 55.0,0.0, 15.0);
INSERT INTO Device (DEVICE_ID,ip,device_name, room, device_purpose, device_type, state, electricity_basic_usage, water_basic_usage, electricity_basic_production, runtime)
VALUES (3,'192.168.1.102', 'Smart SOLAR_PANEL', 'Roof', 'PRODUCER', 'SOLAR_PANEL', false, 0.0, 0.0,150.0, 0.0);
INSERT INTO DEVICE (DEVICE_ID,ip, device_name, room, device_purpose, device_type, state, electricity_basic_usage, water_basic_usage, electricity_basic_production, runtime)
VALUES (4,'192.168.1.104', 'Smart Car', 'Garage', 'CONSUMER', 'CAR', false, 250.0, 0.0,0.0, 10.0);


INSERT INTO DEVICE_RUN_STATS (ID,device_id, date, AVERAGE_ELECTRICITY_USAGE, AVERAGE_WATER_USAGE,electricity_usage_for_run,water_usage_for_run )
VALUES (1,1, '2023-07-04', 20.0, 35.0,40.0,60.0);
INSERT INTO DEVICE_RUN_STATS (ID,device_id, date, AVERAGE_ELECTRICITY_USAGE, AVERAGE_WATER_USAGE,electricity_usage_for_run,water_usage_for_run )
VALUES (2,2, '2023-07-04', 30.0, 75.0,45.0,50.0);
INSERT INTO DEVICE_RUN_STATS (ID,device_id, date, AVERAGE_ELECTRICITY_USAGE, AVERAGE_WATER_USAGE,electricity_usage_for_run,water_usage_for_run )
VALUES (3,4, '2023-07-04', 80.0, 0.0,55.0,50.0);

INSERT INTO SCHEDULE (SCHEDULE_ID,DEVICE_ID,SCHEDULE_BEGINN,SCHEDULE_END)
VALUES (1,1, '15.00', 20.0);
INSERT INTO SCHEDULE (SCHEDULE_ID,DEVICE_ID,SCHEDULE_BEGINN,SCHEDULE_END)
VALUES (2,2, '18.00', 20.0);
INSERT INTO SCHEDULE (SCHEDULE_ID,DEVICE_ID,SCHEDULE_BEGINN,SCHEDULE_END)
VALUES (3,4, '18.00', 6.0);

INSERT INTO TARIF_ENTITY (TARIF_ID,electricity_price,water_price,DATE,TIME)
VALUES (1,0.25,0.30, '2023-07-04', '15.00');
INSERT INTO TARIF_ENTITY (TARIF_ID,electricity_price,water_price,DATE,TIME)
VALUES (2,0.50,0.40, '2023-07-04', '16.00');


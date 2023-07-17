package de.backend.smarthome_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.backend.smarthome_backend.StaticTimeClass;
import de.backend.smarthome_backend.controller.DeviceController;
import de.backend.smarthome_backend.entity.DeviceElectricityProductionPerHour;
import de.backend.smarthome_backend.simulation.SimulationService;
import de.fhdo.SmartGrid.Observer.TimeObserver;
import de.fhdo.SmartGrid.service.ObserverService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TimeSimulationService extends ObserverService<TimeObserver> {
    private Instant CurrentTime;
    private final MqttService mqttService;
    private final DeviceElectricityProductionPerHourService deviceElectricityProductionPerHourService;
    private final DeviceServiceImpl deviceService;
    private final SimulationService simulationService;

    private LocalTime lastTime = null;

    @Autowired
    public TimeSimulationService(MqttService mqttService, DeviceElectricityProductionPerHourService deviceElectricityProductionPerHourService, DeviceServiceImpl deviceService, SimulationService simulationService) {
        this.mqttService = mqttService;
        this.deviceElectricityProductionPerHourService = deviceElectricityProductionPerHourService;
        this.deviceService = deviceService;
        this.simulationService = simulationService;
    }


    @PostConstruct
    public void init() throws Exception {
        var clientID = "SmartHomeTimeController";
        var topic = "fhdo/time";

        final var client = mqttService.getClient(clientID);

        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());


        client.subscribe(topic, (topic1, message) -> {
            try {
                var time = mapper.readValue(message.getPayload(), DateTime.class);
                var localDateTime = LocalDateTime.of(time.date, time.time)
                        .minusYears(0)
                        .withSecond(0)
                        .withNano(0);
                CurrentTime = localDateTime.toInstant(ZoneOffset.UTC);
                StaticTimeClass.timeAndDate = time;

                if (lastTime == null)
                    lastTime = time.time;

                if (hasHourChanged(time.time, lastTime)) {
                    List<DeviceElectricityProductionPerHour> list = simulationService.createDeviceElectricityProductionPerHour(deviceService.getAllDeviceProducer());
                    for (DeviceElectricityProductionPerHour deviceElectricityProductionPerHour : list) {
                        deviceElectricityProductionPerHourService.saveElectricityProductionPerHour(deviceElectricityProductionPerHour);
                    }
                }
                lastTime = time.time;

                notifyObserversAsync();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Instant getCurrentTime() {
        return CurrentTime;
    }

    @Override
    protected void updateObserver(TimeObserver observer) {
        observer.timeUpdated();
    }

    public static class DateTime {

        public DateTime() {
            date = LocalDate.now();
            time = LocalTime.now();
        }
        public LocalDate date;
        public LocalTime time;
        public double accelerationFactor;
    }

    public boolean hasHourChanged(LocalTime dateTime, LocalTime lastTime) {
        return lastTime.getHour() != dateTime.getHour();
    }
}

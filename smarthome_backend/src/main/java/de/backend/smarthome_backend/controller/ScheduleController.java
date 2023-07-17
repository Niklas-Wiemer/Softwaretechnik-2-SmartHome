package de.backend.smarthome_backend.controller;

import de.backend.smarthome_backend.StaticSmartHome;
import de.backend.smarthome_backend.entity.Schedule;
import de.backend.smarthome_backend.service.MqttServiceForGrid;
import de.backend.smarthome_backend.service.ScheduleService;
import jakarta.validation.Valid;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The ScheduleController class handles HTTP requests related to schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final MqttServiceForGrid service;

    /**
     * Constructs a new ScheduleController with the given dependencies.
     *
     * @param scheduleService The ScheduleService used for schedule operations.
     * @param service         The MqttServiceForGrid used for MQTT communication.
     */
    @Autowired
    public ScheduleController(ScheduleService scheduleService, MqttServiceForGrid service) {
        this.scheduleService = scheduleService;
        this.service = service;
    }

    /**
     * Saves a schedule.
     *
     * @param schedule The schedule to be saved.
     * @return The saved schedule as a ResponseEntity.
     * @throws MqttException if an error occurs during MQTT communication.
     */
    @PostMapping()
    public ResponseEntity<Schedule> saveSchedule(@Valid @RequestBody Schedule schedule) throws MqttException {
        scheduleService.saveSchedule(schedule);
        schedule.setSmartHomeId((long) StaticSmartHome.smartHomeId);
        service.sendScheduleToGrid(schedule);
        return ResponseEntity.ok(scheduleService.saveSchedule(schedule));
    }

    /**
     * Retrieves a list of schedules.
     *
     * @return The list of schedules as a ResponseEntity.
     */
    @GetMapping("")
    public ResponseEntity<List<Schedule>> fetchScheduleList() {
        List<Schedule> scheduleList = scheduleService.fetchScheduleList();
        return ResponseEntity.ok(scheduleList);
    }

    /**
     * Updates a schedule with the specified ID.
     *
     * @param schedule The updated schedule.
     * @param id       The ID of the schedule to be updated.
     * @return A ResponseEntity indicating the success of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSchedule(@RequestBody Schedule schedule, @PathVariable("id") Long id) {
        try {
            scheduleService.updateSchedule(schedule, id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a schedule with the specified ID.
     *
     * @param id The ID of the schedule to be deleted.
     * @return A success message as a ResponseEntity.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable("id") Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok().build();
    }
}

package de.backend.smarthome_backend.controller;

import de.backend.smarthome_backend.entity.DeviceRunStats;
import de.backend.smarthome_backend.service.DeviceRunStatsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/deviceRunStats")
public class DeviceRunStatsController {

    private final DeviceRunStatsService deviceRunStatsService;

    @Autowired
    public DeviceRunStatsController(DeviceRunStatsService deviceRunStatsService) {
        this.deviceRunStatsService = deviceRunStatsService;
    }

    /**
     * Saves a new deviceUsageStatsByDay entry.
     *
     * @param deviceRunStats the deviceRunStats object to be saved
     * @return a copy of the newly added deviceRunStats entry
     */
    @PostMapping("")
    public ResponseEntity<DeviceRunStats> saveDeviceRunStats(@Valid @RequestBody DeviceRunStats deviceRunStats) {
        DeviceRunStats deviceRunStatsTemp = deviceRunStatsService.saveDeviceRunStats(deviceRunStats);
        return ResponseEntity.ok(deviceRunStatsTemp);
    }

    /**
     * Fetches a list of all deviceRunStats entries.
     *
     * @return a list of all deviceRunStats entries
     */
    @GetMapping("")
    public ResponseEntity<List<DeviceRunStats>> fetchDeviceRunList() {
        List<DeviceRunStats> deviceRunStatsList = deviceRunStatsService.fetchDeviceRunList();
        return ResponseEntity.ok(deviceRunStatsList);
    }

    /**
     * Updates an existing deviceRunStats entry.
     *
     * @param deviceRunStats the updated deviceRunStats object
     * @param id                    the ID of the deviceRunStats entry to be updated
     * @return a copy of the updated version of the deviceRunStats entry
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDeviceRunStats(@RequestBody DeviceRunStats deviceRunStats, @PathVariable("id") Long id) {
        try {
            deviceRunStatsService.updateDeviceRunStats(deviceRunStats, id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a deviceRunStats entry.
     *
     * @param id the ID of the deviceRunStats entry to be deleted
     * @return a confirmation response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceRunStats(@PathVariable("id") Long id) {
        deviceRunStatsService.deleteDeviceRunStats(id);
        return ResponseEntity.ok().build();
    }
}


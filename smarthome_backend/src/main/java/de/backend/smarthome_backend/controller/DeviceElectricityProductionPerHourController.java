package de.backend.smarthome_backend.controller;

import de.backend.smarthome_backend.entity.DeviceElectricityProductionPerHour;
import de.backend.smarthome_backend.service.DeviceElectricityProductionPerHourService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deviceElectricityProductionPerHour")
public class DeviceElectricityProductionPerHourController {

    private final DeviceElectricityProductionPerHourService service;
    @Autowired
    public DeviceElectricityProductionPerHourController(DeviceElectricityProductionPerHourService service){
        this.service = service;
    }

    /**
     * Saves a new DeviceElectricityProductionPerHour entry.
     *
     * @param statsPerHour The DeviceElectricityProductionPerHour object to be saved.
     * @return ResponseEntity containing a copy of the new added statsPerHour entry.
     */
    @PostMapping()
    public ResponseEntity<DeviceElectricityProductionPerHour> saveDeviceElectricityProductionPerHour(@Valid @RequestBody DeviceElectricityProductionPerHour statsPerHour) {
        DeviceElectricityProductionPerHour newEntry = service.saveElectricityProductionPerHour(statsPerHour);
        return ResponseEntity.ok(newEntry);
    }

    /**
     * Fetches a list of all DeviceElectricityProductionPerHour entries.
     *
     * @return ResponseEntity containing a list of all statsPerHour entries.
     */
    @GetMapping()
    public ResponseEntity<List<DeviceElectricityProductionPerHour>> fetchDeviceElectricityProductionPerHourList() {
        List<DeviceElectricityProductionPerHour> list = service.fetchElectricityProductionPerHourList();
        return ResponseEntity.ok(list);
    }

    /**
     * Updates an existing DeviceElectricityProductionPerHour entry.
     *
     * @param deviceElectricityProductionPerHour The updated DeviceElectricityProductionPerHour object.
     * @param id                                   The ID of the entry to be updated.
     * @return ResponseEntity indicating the success of the update operation.
     *         If successful, returns a copy of the updated version of the DeviceElectricityProductionPerHour entry.
     *         If the entry is not found, returns a not found response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDeviceElectricityProductionPerHour(@RequestBody DeviceElectricityProductionPerHour deviceElectricityProductionPerHour, @PathVariable("id") Long id) {
        try {
            service.updateElectricityProductionPerHour(deviceElectricityProductionPerHour, id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a DeviceElectricityProductionPerHour entry by ID.
     *
     * @param id The ID of the entry to be deleted.
     * @return ResponseEntity indicating the success of the delete operation.
     *         If successful, returns a confirmation response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceElectricityProductionPerHourById(@PathVariable("id") Long id) {
        service.deleteElectricityProductionPerHour(id);
        return ResponseEntity.ok().build();
    }
}
